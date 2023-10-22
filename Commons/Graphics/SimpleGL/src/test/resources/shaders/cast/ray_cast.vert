#version 330 core

layout (location = 0) in vec2 aPos;

out vec2 texCoord;
out vec3 seeDir;

void main()
{
    gl_Position = vec4(aPos, 0, 1.0);
    texCoord = aPos;
    seeDir = normalize(vec3(1, aPos.yx));
}