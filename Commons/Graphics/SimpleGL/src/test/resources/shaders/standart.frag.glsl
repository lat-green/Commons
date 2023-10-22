#version 330 core

out vec4 color;

in vec3 norm;

void main()
{
    color = vec4(norm, 1);
} 