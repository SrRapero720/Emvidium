package me.cortex.nvidium.mixin.minecraft;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import me.cortex.nvidium.Nvidium;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Window.class)
public class MixinWindow {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;assertInInitPhase()V"))
    private void headInit() {
        RenderSystem.assertInInitPhase();
        Nvidium.preWindowInit();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL;createCapabilities()Lorg/lwjgl/opengl/GLCapabilities;"))
    private GLCapabilities init() {
        var cap = GL.createCapabilities();
        Nvidium.checkSystemIsCapable();
        Nvidium.setupGLDebugCallback();
        return cap;
    }
}
