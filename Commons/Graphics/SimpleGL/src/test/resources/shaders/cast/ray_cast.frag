#version 330 core

#define DIST_EPS 0.001f
#define repeat(c) for(int _ = 0; _ < c; _++)
#define REPEAT 100
#define STEPS 10

in vec2 texCoord;
in vec3 seeDir;

uniform vec2 u_seed1;
uniform vec2 u_seed2;

uniform vec2 u_mouse;
uniform vec3 viewPos;

uvec4 R_STATE;

struct Material {
    vec3 color;
    float diffuse;
    float metallic;
};

struct Circle {
    vec3 center;
    float radius;
    Material material;
};

struct ColorStep {
    Material material;
    vec3 normal;
    bool isLigth;
};

uniform sampler2D   u_sample;
uniform float       u_sample_part;

uniform vec2       u_resolution;

uniform Circle[100] circle;
uniform int circle_count;

mat2 rot(float a) {
    float s = sin(a);
    float c = cos(a);
    return mat2(c, -s, s, c);
}

uint TausStep(uint z, int S1, int S2, int S3, uint M)
{
    uint b = (((z << S1) ^ z) >> S2);
    return (((z & M) << S3) ^ b);
}

uint LCGStep(uint z, uint A, uint C)
{
    return (A * z + C);
}

vec2 hash22(vec2 p)
{
    p += u_seed1.x;
    vec3 p3 = fract(vec3(p.xyx) * vec3(.1031, .1030, .0973));
    p3 += dot(p3, p3.yzx+33.33);
    return fract((p3.xx+p3.yz)*p3.zy);
}

float random()
{
    R_STATE.x = TausStep(R_STATE.x, 13, 19, 12, uint(4294967294));
    R_STATE.y = TausStep(R_STATE.y, 2, 25, 4, uint(4294967288));
    R_STATE.z = TausStep(R_STATE.z, 3, 11, 17, uint(4294967280));
    R_STATE.w = LCGStep(R_STATE.w, uint(1664525), uint(1013904223));
    return 2.3283064365387e-10 * float((R_STATE.x ^ R_STATE.y ^ R_STATE.z ^ R_STATE.w));
}

vec3 randomOnSphere() {
    vec3 rand = vec3(random(), random(), random());
    float theta = rand.x * 2.0 * 3.14159265;
    float v = rand.y;
    float phi = acos(2.0 * v - 1.0);
    float r = pow(rand.z, 1.0 / 3.0);
    float x = r * sin(phi) * cos(theta);
    float y = r * sin(phi) * sin(theta);
    float z = r * cos(phi);
    return vec3(x, y, z);
}

float floorDistance(vec3 point) {
    return point.y + 3;
}

float circleDistance(Circle circle, vec3 point) {
    return length(point - circle.center) - circle.radius;
}

vec3 circleNormal(Circle circle, vec3 point) {
    return normalize(point - circle.center);
}

vec3 light = normalize(vec3(-0.5, 0.75, -1.0));

vec3 skybox(vec3 rd) {
    return vec3(1);
//    return texture(skybox, rd).rgb;
    float a = dot(light, rd);
    if(a < 0)
        a = 0;
    else
        a = pow(a, 16);
    return vec3(a + 0.35, a + 0.35, 0.75 - a);
}

ColorStep get_color_step(inout vec3 point, vec3 dir) {
    repeat(REPEAT) {
        float dist = 1000000f / REPEAT;
        ColorStep step;
        for(int i = 0; i < circle_count; i++) {
            float dist_i = circleDistance(circle[i], point);
            if(dist_i < dist) {
                dist = dist_i;
                step = ColorStep(circle[i].material, circleNormal(circle[i], point), false);
            }
        }
        {
            float dist_i = floorDistance(point);
            if(dist_i < dist) {
                dist = dist_i;
                step = ColorStep(Material(vec3(.5f), 0, 0), vec3(0, 1, 0), false);
            }
        }
        if(dist < DIST_EPS)
            return step;
        point += dist * dir;
    }
    return ColorStep(Material(skybox(dir), 1, 0), -dir, true);
}

vec3 get_color(vec3 point, vec3 dir) {
    vec3 color = vec3(1);
    repeat(STEPS) {
        ColorStep step = get_color_step(point, dir);
        color *= step.material.color;
        if(step.isLigth)
            return color;
//        vec3 r = randomOnSphere();
//        dir = reflect(dir, normalize(step.material.diffuse * r * dot(r, step.normal)));
        dir = reflect(dir, step.normal);
        point += dir * DIST_EPS;
    }
    return vec3(0, 0, 0);
}

void main()
{
    vec2 uv = (texCoord - 0.5) * u_resolution / u_resolution.y;
    vec2 uvRes = hash22(uv + 1.0) * u_resolution + u_resolution;
    R_STATE.x = uint(u_seed1.x + uvRes.x);
    R_STATE.y = uint(u_seed1.y + uvRes.x);
    R_STATE.z = uint(u_seed2.x + uvRes.y);
    R_STATE.w = uint(u_seed2.y + uvRes.y);

    vec3 rayDirection = seeDir;
    rayDirection.yx *= rot(u_mouse.y);
    rayDirection.xz *= rot(u_mouse.x);

    vec3 col = vec3(0.0);
    int samples = 4;
    for(int i = 0; i < samples; i++) {
        col += get_color(viewPos, rayDirection);
    }
    col /= samples;

//    float white = 20.0;
//    col *= white * 16.0;
//    col = (col * (1.0 + col / white / white)) / (1.0 + col);

    vec3 sampleCol = texture(u_sample, (texCoord + 1) / 2).rgb;
    col = mix(sampleCol, col, u_sample_part);

    gl_FragColor = vec4(col, 1.0);
}