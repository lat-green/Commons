open module commons.graphics.smart {
    requires transitive commons.graphics.image;
    requires transitive commons.math;
    exports com.greentree.commons.graphics.smart;
    exports com.greentree.commons.graphics.smart.texture;
    exports com.greentree.commons.graphics.smart.pipeline;
    exports com.greentree.commons.graphics.smart.mesh;
    exports com.greentree.commons.graphics.smart.shader;
    exports com.greentree.commons.graphics.smart.shader.material;
    exports com.greentree.commons.graphics.smart.target;
    exports com.greentree.commons.graphics.smart.scene;
}
