package com.greentree.graphics.core;

import com.greentree.common.graphics.sgl.GLFWCallback;
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSeverity;
import com.greentree.commons.util.time.PointTimer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.MemoryStack;

import java.util.ArrayList;
import java.util.Arrays;

import static com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget.DEBUG_OUTPUT;
import static com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget.DEBUG_OUTPUT_SYNCHRONOUS;
import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLShaderParam_int_vec.COMPILE_STATUS;
import static java.lang.StrictMath.sqrt;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL45.*;

public class VertexArrayBindingDivisorDemo {

    private static final String VERTEX_SHADER = """           
#version 330 core
 
layout (location = 0) in vec2 position;
layout (location = 1) in mat4 model;

void main()
{
    gl_Position = model * vec4(position.x, position.y, 0, 1);
};
""";
    private static final String FRAGMENT_SHADER = """            
#version 330 core

out vec4 color;

void main()
{
    color = vec4(1.0f, 0.5f, 0.0f, 1.0f);
};
""";

    public static void main(String[] args) {
        glfwInit();

        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        final var w = glfwCreateWindow(800, 600, "", 0, 0);

        glfwMakeContextCurrent(w);
        glfwSwapInterval(1);

        GL.createCapabilities(false);

        glClearColor(.6f, .6f, .6f, 1);
        glClearDepth(1.0);

        glEnable(DEBUG_OUTPUT.glEnum);
        glEnable(DEBUG_OUTPUT_SYNCHRONOUS.glEnum);
        glDebugMessageCallback(GLFWCallback.gl((source, type, id, severity, message, userParam) -> {
            if (severity != GLDebugSeverity.DEBUG_SEVERITY_NOTIFICATION) {
                final var severity0 = severity.name;
                var source0 = source.name;
                var type0 = type.name;
                throw new RuntimeException("openGl error (Source: %s Type: %s ID: %d Severity: %s) message:%s\n".formatted(source0, type0,
                        id, severity0, message));
            }
        }), 0);

        var vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        try(final var stack = MemoryStack.create().push()) {
            glBufferData(GL_ARRAY_BUFFER, stack.floats(
                    1,  1,
                    1, -1,
                    -1, -1,

                    1,  1,
                    -1, -1,
                    -1,  1
            ), GL_STATIC_DRAW);
        }
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        var c = (int) sqrt(30_000_000);
        final var mCount = c * c;
        System.out.println(c);
        System.out.println(mCount);

        var ubo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, ubo);
        try(final var stack = MemoryStack.create(4 * 16 * mCount).push()) {
//            var arr = new float[16 * matrixes.length];
//            for(int i = 0; i < matrixes.length; i++) {
//                var m = matrixes[i];
//                m.get(arr, i * 16);
//            }
//            var buf = stack.floats(arr);

            var arr = new float[16];
            var buf = stack.mallocFloat(16 * mCount);
            for(int x = -c/2; x < c/2; x++) {
//             System.out.println((x + c / 2 +0.0) / c * 100 + "%");
                for (int y = -c / 2; y < c / 2; y++) {
                    var m = new Matrix4f().scale(2f / c).translate(x + .5f, y + .5f, 0).scale(1f / 2 / 2);
                    m.get(arr);
                    buf.put(arr);
                }
            }
            buf.flip();

//            var buf = stack.mallocFloat(16 * matrixes.length);
//            for(var m : matrixes)
//                m.get(buf);
//            buf.rewind();
            glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
        }
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        var vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * 4, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, ubo);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glEnableVertexAttribArray(4);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 4 * 4 * 4, 0 * 4 * 4);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 4 * 4 * 4, 1 * 4 * 4);
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 4 * 4 * 4, 2 * 4 * 4);
        glVertexAttribPointer(4, 4, GL_FLOAT, false, 4 * 4 * 4, 3 * 4 * 4);
        glVertexAttribDivisor(1, 1);
        glVertexAttribDivisor(2, 1);
        glVertexAttribDivisor(3, 1);
        glVertexAttribDivisor(4, 1);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        var v = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(v, VERTEX_SHADER);
        glCompileShader(v);
        checkShaderCompileStatus(v);
        var f = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(f, FRAGMENT_SHADER);
        glCompileShader(f);
        checkShaderCompileStatus(f);

        var p = glCreateProgram();
        glAttachShader(p, v);
        glAttachShader(p, f);
        glLinkProgram(p);
        glDeleteShader(v);
        glDeleteShader(f);

        glBindVertexArray(vao);
        glUseProgram(p);

        while(!glfwWindowShouldClose(w)) {
            var t = new PointTimer();
            t.point();
            glClear(GL_COLOR_BUFFER_BIT);
            glDrawArraysInstanced(GL_TRIANGLES, 0, 6, mCount);
            glfwSwapBuffers(w);
            glfwPollEvents();
            t.point();
//            System.out.println(1.0 / t.get(0));
        }

        glfwTerminate();
    }

    private static void checkShaderCompileStatus(final int id) {
        final int[] errc = {1};
        glGetShaderiv(id, COMPILE_STATUS.glEnum, errc);
        if(0 == errc[0])
            throw new IllegalStateException(
                    String.format("Error creating shader: %s", glGetShaderInfoLog(id)));
    }
}
