#version 330 core

invariant gl_Position;

uniform mat4 mvp;
uniform mat4 nm;

layout(location = 0) in vec3 vertex_coord;
layout(location = 1) in vec3 vertex_normal;

invariant out vec3 norm;

void main(void) {
	vec4 vcoord = vec4( vertex_coord, 1.0 );
	norm = normalize( nm * vec4(vertex_normal, 0.0) ).xyz;
	gl_Position = mvp * vcoord;
}
