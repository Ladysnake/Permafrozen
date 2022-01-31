#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

uniform mat4 ProjInverseMat;
uniform mat4 ViewInverseMat;

uniform float Darkness;
uniform float Thickness;

in vec2 texCoord;

out vec4 fragColor;

#define LOG2 1.442695

vec3 playerSpace(vec2 uv, float depth)
{
    vec3 ndc = vec3(uv, depth) * 2.0 - 1.0;
    vec4 posCS = vec4(ndc, 1.0);
    vec4 posVS = ProjInverseMat * posCS;
    posVS /= posVS.w;
    vec4 posPS = ViewInverseMat * posVS;
    return posPS.xyz;
}

vec3 applyFog(vec3 rgb, float distance, float darkness)
{
    float fogAmount = 0.4 + 0.6 * sqrt(distance / Thickness);
    fogAmount = clamp(fogAmount, 0.0, 1.0);

    vec3 fogColor = mix(vec3(0.5, 0.6, 0.7), vec3(0.3, 0.31, 0.3), darkness);
    return mix(rgb, fogColor, fogAmount);
}

void main()
{
    vec3 color = texture(DiffuseSampler, texCoord).rgb;
    float depth = texture(DiffuseDepthSampler, texCoord).r;

    vec3 posPS = playerSpace(texCoord, depth);
    float dstToSurface = length(posPS);

    fragColor = vec4(applyFog(color, dstToSurface, Darkness), 1.0);
}