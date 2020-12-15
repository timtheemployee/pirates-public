package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class TextureSkeleton {

    private final TextureSkeletonEntity anchorEntity;
    private final List<TextureSkeletonEntity> entities = new ArrayList<TextureSkeletonEntity>();

    public TextureSkeleton(TextureSkeletonEntity anchorEntity) {
        this.anchorEntity = anchorEntity;
        entities.add(anchorEntity);
        Vector2 position = new Vector2();
        anchorEntity.bounds.getPosition(position);
    }

    public void setAnchorPosition(float x, float y) {
        anchorEntity.bounds.setPosition(x, y);
    }

    public void addEntity(TextureSkeletonEntity entity, TextureSkeletonEntity constraint) {
        if (!entities.contains(constraint))
            throw new IllegalArgumentException("Constraint should already be in skeleton");

        Vector2 constraintPosition = new Vector2();
        constraint.bounds.getPosition(constraintPosition);
        float horizontalStride = constraint.bounds.width * entity.horizontalBias;
        float verticalStride = constraint.bounds.height * entity.verticalBias;
        constraintPosition.add(horizontalStride, verticalStride);
        entity.bounds.setPosition(constraintPosition);

        entities.add(entity);
    }

    public void setHitTextureDrawing(boolean isOpposite) {
        for (TextureSkeletonEntity entity: entities) {
            entity.drawingTexture = isOpposite ? entity.hitTexture : entity.texture;
        }
    }

    public Rectangle getBounds() {
        float minX = Integer.MAX_VALUE;
        float minY = Integer.MAX_VALUE;
        float maxX = Integer.MIN_VALUE;
        float maxY = Integer.MIN_VALUE;

        for (TextureSkeletonEntity entity : entities) {
            minX = Math.min(entity.bounds.x, minX);
            minY = Math.min(entity.bounds.y, minY);
            maxX = Math.max(entity.bounds.x + entity.bounds.width, maxX);
            maxY = Math.max(entity.bounds.y + entity.bounds.height, maxY);
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        for (TextureSkeletonEntity entity : entities) {
            spriteBatch.draw(
                    entity.drawingTexture,
                    entity.bounds.x,
                    entity.bounds.y,
                    entity.bounds.width,
                    entity.bounds.height
            );

            if (entity.drawingTexture == entity.hitTexture) {
                entity.drawingTexture = entity.texture;
            }
        }

        spriteBatch.end();
    }

    public static class TextureSkeletonEntity {

        private final TextureRegion texture;
        private final TextureRegion hitTexture;
        private TextureRegion drawingTexture;
        private final Rectangle bounds;
        private float verticalBias = 0f;
        private float horizontalBias = 0f;

        public TextureSkeletonEntity(TextureRegion texture, TextureRegion hitTexture) {
            this.texture = texture;
            this.hitTexture = hitTexture;
            this.drawingTexture = texture;

            if (texture.getRegionWidth() != hitTexture.getRegionWidth()
                    && texture.getRegionHeight() != hitTexture.getRegionWidth())
                throw new IllegalArgumentException("Hit texture should be same size as main texture");

            bounds = new Rectangle();
            bounds.setWidth(texture.getRegionWidth());
            bounds.setHeight(texture.getRegionHeight());
        }

        public void setVerticalBias(float verticalBias) {
            if (Math.abs(verticalBias) > 1.0f)
                throw new IllegalArgumentException("Vertical bias must be in interval [-1.0; 1.0]");
            this.verticalBias = verticalBias;
        }

        public void setHorizontalBias(float horizontalBias) {
            if (Math.abs(horizontalBias) > 1.0f)
                throw new IllegalArgumentException("Horizontal bias must be in interval [-1.0; 1.0]");
            this.horizontalBias = horizontalBias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TextureSkeletonEntity that = (TextureSkeletonEntity) o;

            if (Float.compare(that.verticalBias, verticalBias) != 0) return false;
            if (Float.compare(that.horizontalBias, horizontalBias) != 0) return false;
            if (texture != null ? !texture.equals(that.texture) : that.texture != null)
                return false;
            return bounds != null ? bounds.equals(that.bounds) : that.bounds == null;
        }

        @Override
        public int hashCode() {
            int result = texture != null ? texture.hashCode() : 0;
            result = 31 * result + (bounds != null ? bounds.hashCode() : 0);
            result = 31 * result + (verticalBias != +0.0f ? Float.floatToIntBits(verticalBias) : 0);
            result = 31 * result + (horizontalBias != +0.0f ? Float.floatToIntBits(horizontalBias) : 0);
            return result;
        }
    }
}
