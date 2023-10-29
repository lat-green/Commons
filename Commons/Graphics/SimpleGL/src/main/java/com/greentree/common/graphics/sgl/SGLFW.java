package com.greentree.common.graphics.sgl;

import com.greentree.common.graphics.sgl.callback.SGLFWErrorCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWJoystickCallbackI;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.container.MultiContainer;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class SGLFW {

    private static final MultiContainer<SGLFWJoystickCallbackI> joystick_callbacks = new MultiContainer<>();
    private static final MultiContainer<SGLFWErrorCallbackI> error_callbacks = new MultiContainer<>();

    private static Thread mainThread;

    protected SGLFW() {
    }

    public static ListenerCloser addCallback(SGLFWErrorCallbackI callback) {
        return error_callbacks.add(callback);
    }

    public static ListenerCloser addCallback(SGLFWJoystickCallbackI callback) {
        return joystick_callbacks.add(callback);
    }

    public static void checkMainThread() {
        if (!isMainThread())
            throw new UnsupportedOperationException("is not main thread");
    }

    public static boolean isMainThread() {
        return isMainThread(Thread.currentThread());
    }

    public static boolean isMainThread(Thread other) {
        return getMainThread().equals(other);
    }

    public static Thread getMainThread() {
        Objects.requireNonNull(mainThread, "SGLFW not init");
        return mainThread;
    }

    public static void checkMainThread(Thread other) {
        if (!isMainThread(other))
            throw new UnsupportedOperationException("is not main thread");
    }

    public static void init() {
        glfwSetErrorCallback(GLFWCallback.gl((SGLFWErrorCallbackI) (glfwError, description) -> {
            for (var l : error_callbacks)
                l.invoke(glfwError, description);
        }));
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to init GLFW");
        }
        glfwSetJoystickCallback(GLFWCallback.gl((SGLFWJoystickCallbackI) (jid, event) -> {
            for (var l : joystick_callbacks)
                l.invoke(jid, event);
        }));
        mainThread = Thread.currentThread();
    }

    public static void terminate() {
        mainThread = null;
        glfwTerminate();
    }

    public enum Boolean {

        TRUE(GLFW_TRUE), FALSE(GLFW_FALSE),
        ;

        public final int glEnum;

        Boolean(int i) {
            glEnum = i;
        }

        public static Boolean get(boolean b) {
            return b ? TRUE : FALSE;
        }

        public static Boolean get(int key) {
            for (var k : values())
                if (key == k.glEnum)
                    return k;
            return null;
        }

        public boolean get() {
            return this == TRUE;
        }
    }

    public enum ButtonAction {

        RELEASE(GLFW_RELEASE), PRESS(GLFW_PRESS), REPEAT(GLFW_REPEAT),
        ;

        public final int glEnum;

        ButtonAction(int glEnum) {
            this.glEnum = glEnum;
        }

        public static ButtonAction get(int glEnum) {
            for (var e : values())
                if (e.glEnum == glEnum)
                    return e;
            return null;
        }
    }

    public enum ConnectedEvent {

        CONNECTED(GLFW_CONNECTED), DISCONNECTED(GLFW_DISCONNECTED),
        ;

        public final int glEnum;

        ConnectedEvent(int glEnum) {
            this.glEnum = glEnum;
        }

        public static ConnectedEvent get(int glEnum) {
            for (var e : values())
                if (e.glEnum == glEnum)
                    return e;
            return null;
        }

    }

    public enum CursorState {

        CURSOR_NORMAL(GLFW_CURSOR_NORMAL), CURSOR_HIDDEN(GLFW_CURSOR_HIDDEN), CURSOR_DISABLED(GLFW_CURSOR_DISABLED),
        ;

        public final int glEnum;

        CursorState(int glEnum) {
            this.glEnum = glEnum;
        }

        public static CursorState get(int glEnum) {
            for (var e : values())
                if (e.glEnum == glEnum)
                    return e;
            return null;
        }
    }

    public enum Error {

        NO_ERROR(GLFW_NO_ERROR), NOT_INITIALIZED(GLFW_NOT_INITIALIZED), NO_CURRENT_CONTEXT(GLFW_NO_CURRENT_CONTEXT),
        INVALID_ENUM(GLFW_INVALID_ENUM), INVALID_VALUE(GLFW_INVALID_VALUE), OUT_OF_MEMORY(GLFW_OUT_OF_MEMORY),
        API_UNAVAILABLE(GLFW_API_UNAVAILABLE), VERSION_UNAVAILABLE(GLFW_VERSION_UNAVAILABLE),
        PLATFORM_ERROR(GLFW_PLATFORM_ERROR), FORMAT_UNAVAILABLE(GLFW_FORMAT_UNAVAILABLE),
        NO_WINDOW_CONTEXT(GLFW_NO_WINDOW_CONTEXT),
        ;

        public final int glEnum;

        Error(int glEnum) {
            this.glEnum = glEnum;
        }

        public static Error get(int glEnum) {
            for (var e : values())
                if (e.glEnum == glEnum)
                    return e;
            return null;
        }

    }

    public enum GLFWKey {

        KEY_0(GLFW_KEY_0), KEY_1(GLFW_KEY_1), KEY_2(GLFW_KEY_2), KEY_3(GLFW_KEY_3), KEY_4(GLFW_KEY_4), KEY_5(GLFW_KEY_5),
        KEY_6(GLFW_KEY_6), KEY_7(GLFW_KEY_7), KEY_8(GLFW_KEY_8), KEY_9(GLFW_KEY_9), A(GLFW_KEY_A), B(GLFW_KEY_B),
        C(GLFW_KEY_C), D(GLFW_KEY_D), E(GLFW_KEY_E), F(GLFW_KEY_F), G(GLFW_KEY_G), H(GLFW_KEY_H), I(GLFW_KEY_I), J(GLFW_KEY_J),
        K(GLFW_KEY_K), L(GLFW_KEY_L), M(GLFW_KEY_M), N(GLFW_KEY_N), O(GLFW_KEY_O), P(GLFW_KEY_P), Q(GLFW_KEY_Q), R(GLFW_KEY_R),
        S(GLFW_KEY_S), T(GLFW_KEY_T), U(GLFW_KEY_U), V(GLFW_KEY_V), W(GLFW_KEY_W), X(GLFW_KEY_X), Y(GLFW_KEY_Y), Z(GLFW_KEY_Z),
        UP(GLFW_KEY_UP), F1(GLFW_KEY_F1), F2(GLFW_KEY_F2), F3(GLFW_KEY_F3), F4(GLFW_KEY_F4), F5(GLFW_KEY_F5), F6(GLFW_KEY_F6),
        F7(GLFW_KEY_F7), F8(GLFW_KEY_F8), F9(GLFW_KEY_F9), TAB(GLFW_KEY_TAB), END(GLFW_KEY_END), F10(GLFW_KEY_F10),
        F11(GLFW_KEY_F11), F12(GLFW_KEY_F12), F13(GLFW_KEY_F13), F14(GLFW_KEY_F14), F15(GLFW_KEY_F15), F16(GLFW_KEY_F16),
        F17(GLFW_KEY_F17), F18(GLFW_KEY_F18), F19(GLFW_KEY_F19), F20(GLFW_KEY_F20), F21(GLFW_KEY_F21), F22(GLFW_KEY_F22),
        F23(GLFW_KEY_F23), F24(GLFW_KEY_F24), F25(GLFW_KEY_F25), LEFT(GLFW_KEY_LEFT), DOWN(GLFW_KEY_DOWN),
        HOME(GLFW_KEY_HOME), KP_0(GLFW_KEY_KP_0), KP_1(GLFW_KEY_KP_1), KP_2(GLFW_KEY_KP_2), KP_3(GLFW_KEY_KP_3),
        KP_4(GLFW_KEY_KP_4), KP_5(GLFW_KEY_KP_5), KP_6(GLFW_KEY_KP_6), KP_7(GLFW_KEY_KP_7), KP_8(GLFW_KEY_KP_8),
        KP_9(GLFW_KEY_KP_9), KP_ADD(GLFW_KEY_KP_ADD), KP_ENTER(GLFW_KEY_KP_ENTER), KP_EQUAL(GLFW_KEY_KP_EQUAL),
        KP_DIVIDE(GLFW_KEY_KP_DIVIDE), MENU(GLFW_KEY_MENU), LAST(GLFW_KEY_LAST), SPACE(GLFW_KEY_SPACE),
        COMMA(GLFW_KEY_COMMA), MINUS(GLFW_KEY_MINUS), SLASH(GLFW_KEY_SLASH), EQUAL(GLFW_KEY_EQUAL), ENTER(GLFW_KEY_ENTER),
        RIGHT(GLFW_KEY_RIGHT), PAUSE(GLFW_KEY_PAUSE), PERIOD(GLFW_KEY_PERIOD), ESCAPE(GLFW_KEY_ESCAPE),
        INSERT(GLFW_KEY_INSERT), DELETE(GLFW_KEY_DELETE), UNKNOWN(GLFW_KEY_UNKNOWN), WORLD_1(GLFW_KEY_WORLD_1),
        WORLD_2(GLFW_KEY_WORLD_2), PAGE_UP(GLFW_KEY_PAGE_UP), NUM_LOCK(GLFW_KEY_NUM_LOCK), LEFT_ALT(GLFW_KEY_LEFT_ALT),
        SEMICOLON(GLFW_KEY_SEMICOLON), BACKSLASH(GLFW_KEY_BACKSLASH), BACKSPACE(GLFW_KEY_BACKSPACE),
        PAGE_DOWN(GLFW_KEY_PAGE_DOWN), CAPS_LOCK(GLFW_KEY_CAPS_LOCK), RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
        APOSTROPHE(GLFW_KEY_APOSTROPHE), KP_DECIMAL(GLFW_KEY_KP_DECIMAL), LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
        LEFT_SUPER(GLFW_KEY_LEFT_SUPER), SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK), KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
        KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT), RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT), RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER),
        LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET), GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT), PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),
        LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL), RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),
        RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
        ;

        public final int glEnum;

        GLFWKey(int i) {
            glEnum = i;
        }

        public static GLFWKey get(int key) {
            for (var k : values())
                if (key == k.glEnum)
                    return k;
            return null;
        }

        public String glfwName() {
            return glfwGetKeyName(glEnum, glfwGetKeyScancode(glEnum));
        }

        @Deprecated
        public int index() {
            return glEnum;
        }

    }

    public enum GLFWMouseButton {

        MOUSE_BUTTON_RIGHT(GLFW_MOUSE_BUTTON_RIGHT), MOUSE_BUTTON_MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
        MOUSE_BUTTON_LEFT(GLFW_MOUSE_BUTTON_LEFT),
        ;

        public final int glEnum;

        GLFWMouseButton(int i) {
            glEnum = i;
        }

        public static GLFWMouseButton get(int key) {
            for (var k : values())
                if (key == k.glEnum)
                    return k;
            return null;
        }

    }

    public enum InputMode {

        LOCK_KEY_MODS(GLFW_LOCK_KEY_MODS), STICKY_KEYS(GLFW_STICKY_KEYS), CURSOR(GLFW_CURSOR),
        ;

        public final int glEnum;

        InputMode(int i) {
            glEnum = i;
        }

        public static InputMode get(int key) {
            for (var k : values())
                if (key == k.glEnum)
                    return k;
            return null;
        }

    }

    public enum KeyMode {

        MOD_SHIFT(GLFW_MOD_SHIFT), MOD_CONTROL(GLFW_MOD_CONTROL), MOD_ALT(GLFW_MOD_ALT), MOD_SUPER(GLFW_MOD_SUPER),
        MOD_CAPS_LOCK(GLFW_MOD_CAPS_LOCK), MOD_NUM_LOCK(GLFW_MOD_NUM_LOCK),
        ;

        public final int glEnum;

        KeyMode(int glEnum) {
            this.glEnum = glEnum;
        }

        public static KeyMode get(int glEnum) {
            for (var k : values())
                if (glEnum == k.glEnum)
                    return k;
            return null;
        }

        public static KeyMode[] gl(int glEnum) {
            final var s = get1inBinary(glEnum);
            final var res = new KeyMode[s];
            var i = 0;
            for (var bit : values())
                if ((bit.glEnum & glEnum) != 0)
                    res[i++] = bit;
            return res;
        }

        private static int get1inBinary(int number) {
            var r = 0;
            while (number > 0) {
                r += number & 1;
                number /= 2;
            }
            return r;
        }

    }

    public enum WindowHint {

        DOUBLEBUFFER(GLFW_DOUBLEBUFFER), VISIBLE(GLFW_VISIBLE), SAMPLES(GLFW_SAMPLES), RESIZABLE(GLFW_RESIZABLE),
        OPENGL_DEBUG_CONTEXT(GLFW_OPENGL_DEBUG_CONTEXT),
        ;

        public final int glEnum;

        WindowHint(int glEnum) {
            this.glEnum = glEnum;
        }
    }

}
