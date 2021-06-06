package com.feroxclient.fabric.clickgui;

import com.feroxclient.fabric.FeroxMod;
import com.feroxclient.fabric.clickgui.components.ModuleButton;
import com.feroxclient.fabric.module.Category;
import com.feroxclient.fabric.module.Module;
import com.feroxclient.fabric.util.MinecraftTrait;
import com.feroxclient.fabric.util.render.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

public class Frame implements MinecraftTrait {

    public ArrayList<Component> components = new ArrayList<>();
    public int x,y,width,height,dragX,dragY,tY;
    public boolean isSettingOpen, isDragging, open;
    public Category category;

    public Frame(Category category, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;
        this.category = category;
        this.tY = this.height;

        for (Module module : FeroxMod.moduleManager.getModules()) {
            if (module.getCategory().equals(category)) {
                ModuleButton modButton = new ModuleButton(module, this, tY);
                components.add(modButton);
                tY += 15;
            }
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY){
        checkBox();
        RenderUtil.drawRect(x, y, width, height, new Color(59, 26, 203));

        mc.textRenderer.draw(matrixStack, category.getName(), x + (width /2) - (mc.textRenderer.getWidth(category.getName()) /2) ,y + (height /2) - (mc.textRenderer.fontHeight /2) ,-1);

        for (Component component : components) {
            if (open && !components.isEmpty()) {
                component.renderComponent(matrixStack);
            }
            component.updateComponent(mouseX, mouseY);
        }
        RenderUtil.drawOutlineRect(x, y, width, height + (components.size() * 15), new Color(255, 0, 101));
        RenderUtil.drawLine(x,y, x + width, y, new Color(255, 0, 101));
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton){
        if (isWithinHeader((int) mouseX, (int) mouseY) && mouseButton == 0) {
            setDragging(true);
            dragX = (int) (mouseX - getX());
            dragY = (int) (mouseY - getY());
        } else if (isWithinHeader((int) mouseX, (int) mouseY) && mouseButton == 1) {
            setOpen(!isOpen());
        } else if (isOpen() && !getComponents().isEmpty()) {
            for (Component component : getComponents()) {
                component.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
            }
        }
    }

    public void keyTyped( int keyCode){
        if (isOpen() && !getComponents().isEmpty() && keyCode != 1) {
            for (Component component : getComponents()) {
                component.keyTyped(keyCode);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        setDragging(false);

        if (isOpen() && !getComponents().isEmpty()) {
            for (Component component : getComponents()) {
                component.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

        public boolean isWithinHeader(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        int newX = mouseX - dragX;
        int newY = mouseY - dragY;

        if (this.isDragging) {
            this.setX(newX);
            this.setY(newY);
        }
    }

    public void checkBox(){
        if(x <= 0) setX(0);
        if(x >= mc.currentScreen.width - width) setX(mc.currentScreen.width - width);
        if(y <= 0) setY(0);
        if(y >= mc.currentScreen.height - height + (components.size() * 15)) setY(mc.currentScreen.height - height + (components.size() * 15));
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDragging(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public Category getCategory() {
        return category;
    }
}
