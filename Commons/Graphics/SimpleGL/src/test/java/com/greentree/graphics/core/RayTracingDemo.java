package com.greentree.graphics.core;

import com.greentree.common.graphics.sgl.GLFWCallback;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.callback.SGLFWCursorPosCallbackI;
import com.greentree.common.graphics.sgl.enums.gl.GLError;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSeverity;
import com.greentree.common.graphics.sgl.enums.gl.param.name.GLShaderParam_int_vec;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.quaternion.Quaternion;
import com.greentree.commons.math.vector.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget.DEBUG_OUTPUT;
import static com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget.DEBUG_OUTPUT_SYNCHRONOUS;
import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLShaderParam_int_vec.COMPILE_STATUS;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;

public class RayTracingDemo {

    private static final String TEX_VERTEX_SHADER = readText("src\\test\\resources\\shaders\\tex\\tex.vert");
    private static final String TEX_FRAGMENT_SHADER = readText("src\\test\\resources\\shaders\\tex\\tex.frag");
    private static final String RAY_VERTEX_SHADER = readText("src\\test\\resources\\shaders\\cast\\ray_cast.vert");
    private static final String RAY_FRAGMENT_SHADER = readText("src\\test\\resources\\shaders\\cast\\ray_cast.frag");

    private static String readText(String fileName) {
        try(final var in = new FileInputStream(fileName)) {
            return new String(in.readAllBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final float SPEED = 1;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int RANDOM_LIMIT = 100;
    private static float mouseX, mouseY;
    private static float positionX, positionY, positionZ;
    private static int speedX, speedY, speedZ;
    private static SGLFW.ButtonAction w, s, a, d, up, down;

    private static float framesStill = 1f;



    public static void main(String[] args) throws InterruptedException {
        glfwInit();

        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        final var window = glfwCreateWindow(WIDTH, HEIGHT, "", 0, 0);

        glfwMakeContextCurrent(window);
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

        var t1 = texture(WIDTH, HEIGHT);
        var t2 = texture(WIDTH, HEIGHT);

        var fb1 = FreameBuffer(WIDTH, HEIGHT, t1);
        var fb2 = FreameBuffer(WIDTH, HEIGHT, t2);

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

        var vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * 4, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        glActiveTexture(GL_TEXTURE0 + 1);
        glBindTexture(GL_TEXTURE_2D, t1);
        glActiveTexture(GL_TEXTURE0 + 2);
        glBindTexture(GL_TEXTURE_2D, t2);
        glActiveTexture(GL_TEXTURE0 + 0);

        glfwSetCursorPosCallback(window, GLFWCallback.gl((SGLFWCursorPosCallbackI) (x, y) -> {
            mouseX = (float) x;
            mouseY = (float) y;
            framesStill = 1;
        }));
        glfwSetKeyCallback(window, GLFWCallback.gl((key, scancode, action, mods) -> {
            if(action == SGLFW.ButtonAction.REPEAT)
                return;
            if(key == SGLFW.GLFWKey.ESCAPE)
                glfwSetWindowShouldClose(window, true);
            if(key == SGLFW.GLFWKey.W)
                w = action;
            if(key == SGLFW.GLFWKey.S)
                s = action;
            if(key == SGLFW.GLFWKey.A)
                a = action;
            if(key == SGLFW.GLFWKey.D)
                d = action;
            if(key == SGLFW.GLFWKey.SPACE)
                up = action;
            if(key == SGLFW.GLFWKey.LEFT_SHIFT)
                down = action;
            speedX = 0;
            speedY = 0;
            speedZ = 0;
            if(w == SGLFW.ButtonAction.PRESS)
                speedX++;
            if(s == SGLFW.ButtonAction.PRESS)
                speedX--;
            if(a == SGLFW.ButtonAction.PRESS)
                speedZ++;
            if(d == SGLFW.ButtonAction.PRESS)
                speedZ--;
            if(up == SGLFW.ButtonAction.PRESS)
                speedY++;
            if(down == SGLFW.ButtonAction.PRESS)
                speedY--;
            framesStill = 1;
        }));
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        var texProgram = program(TEX_VERTEX_SHADER, TEX_FRAGMENT_SHADER);
        var rayProgram = program(RAY_VERTEX_SHADER, RAY_FRAGMENT_SHADER);

        glUseProgram(rayProgram);
        glUniform1i(glGetUniformLocation(rayProgram, "u_sample"), 1);

        glUniform3f(glGetUniformLocation(rayProgram, "viewPos"), 0, 0, 0);

        glUniform1i(glGetUniformLocation(rayProgram, "circle_count"), 10);

        glUniform2f(glGetUniformLocation(rayProgram, "u_resolution"), WIDTH, HEIGHT);

        for(int i = 0; i < 10; i++) {
            glUniform3f(glGetUniformLocation(rayProgram, "circle["+i+"].material.color"), 1, 0, 0);
            glUniform1f(glGetUniformLocation(rayProgram, "circle["+i+"].material.metallic"), 1);
            glUniform1f(glGetUniformLocation(rayProgram, "circle["+i+"].material.diffuse"), 0);
            glUniform3f(glGetUniformLocation(rayProgram, "circle["+i+"].center"), 2.5f * i - 10, 2, 15);
            glUniform1f(glGetUniformLocation(rayProgram, "circle["+i+"].radius"), 1);
        }

        var rand = new Random();

        glBindVertexArray(vao);
        while(!glfwWindowShouldClose(window)) {
            glUseProgram(rayProgram);

            glUniform2f(glGetUniformLocation(rayProgram, "u_seed1"), rand.nextInt(RANDOM_LIMIT) * 999.0f, rand.nextInt(RANDOM_LIMIT) * 999.0f);
            glUniform2f(glGetUniformLocation(rayProgram, "u_seed2"), rand.nextInt(RANDOM_LIMIT) * 999.0f, rand.nextInt(RANDOM_LIMIT) * 999.0f);

            var move = new Vector3f(speedX, speedY, speedZ);
            move.timesAssign(SPEED);

            var rotation = new Quaternion();
            rotation.identity();
            rotation.rotateY(mouseX * Mathf.PI);
            rotation.rotateX(mouseY * Mathf.PI);

            System.out.println(rotation);

            System.out.println(mouseX);
            System.out.println(mouseY);

            var direction = rotation.times(move);

            positionX += direction.x();
            positionY += direction.y();
            positionZ += direction.z();

            glUniform3f(glGetUniformLocation(rayProgram, "viewPos"), positionX, positionY, positionZ);
            glUniform2f(glGetUniformLocation(rayProgram, "u_mouse"), mouseX / WIDTH, mouseY / HEIGHT);
            glUniform1f(glGetUniformLocation(rayProgram, "u_sample_part"), framesStill);
            glBindFramebuffer(GL_FRAMEBUFFER, fb2);
            glDrawArrays(GL_TRIANGLES, 0, 6);

            glUseProgram(texProgram);
            glBindFramebuffer(GL_FRAMEBUFFER, fb1);
            glUniform1i(glGetUniformLocation(texProgram, "u_sample"), 2);
            glDrawArrays(GL_TRIANGLES, 0, 6);

            glBindFramebuffer(GL_FRAMEBUFFER, 0);
            glUniform1i(glGetUniformLocation(texProgram, "u_sample"), 1);
            glDrawArrays(GL_TRIANGLES, 0, 6);
            glfwSwapBuffers(window);
            glfwPollEvents();
            framesStill *= 1 - .1f;
        }

        glfwTerminate();
    }

    private static int FreameBuffer(int width, int height, int texture) {
        var fb = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fb);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
//        var rbo = glGenRenderbuffers();
//        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
//        glBindRenderbuffer(GL_RENDERBUFFER, 0);
//        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        return fb;
    }

    private static int texture(int width, int height) {
        var texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE,0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        return texture;
    }

    private static int program(String vertexText, String fragmentText) {
        var v = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(v, vertexText);
        glCompileShader(v);
        checkShaderCompileStatus(v);
        var f = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(f, fragmentText);
        glCompileShader(f);
        checkShaderCompileStatus(f);

        var program = glCreateProgram();
        glAttachShader(program, v);
        glAttachShader(program, f);
        glLinkProgram(program);
        glDeleteShader(v);
        glDeleteShader(f);

        validateParam(program, GLShaderParam_int_vec.LINK_STATUS, "Shader program link error:");
        glValidateProgram(program);
        validateParam(program, GLShaderParam_int_vec.VALIDATE_STATUS, "Shader program validate error:");

        return program;
    }

    private static void checkShaderCompileStatus(final int id) {
        final int[] errc = {1};
        glGetShaderiv(id, COMPILE_STATUS.glEnum, errc);
        if(0 == errc[0])
            throw new IllegalStateException(
                    String.format("Error creating shader: %s", glGetShaderInfoLog(id)));
    }

    private static void validateParam(int glID, final GLShaderParam_int_vec pname, final String errFormat) {
        final var errc = new int[1];
        glGetProgramiv(glID, pname.glEnum, errc);
        if(GLError.NO_ERROR.glEnum == errc[0]) {
            glGetProgramiv(glID, GLShaderParam_int_vec.INFO_LOG_LENGTH.glEnum, errc);
            var s = glGetProgramInfoLog(glID, errc[0]);
            System.err.printf("%s %s\n", errFormat, s);
        }
    }

}
