#version 330 core

out vec4 color;

in vec2 texCoord;

uniform sampler2D   u_sample;

void main()
{
    color = texture(u_sample, texCoord);
}