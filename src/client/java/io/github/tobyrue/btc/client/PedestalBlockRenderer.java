package io.github.tobyrue.btc.client;

import io.github.tobyrue.btc.block.entity.PedestalBlockEntity;
import io.github.tobyrue.btc.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class PedestalBlockRenderer implements BlockEntityRenderer<PedestalBlockEntity>
{
    private static ItemStack stack = new ItemStack(ModItems.STAFF, 1);
    //private static ItemStack key1 = new ItemStack(ModItems.RUBY_TRIAL_KEY, 1);

    private final ItemRenderer itemRenderer;

    public PedestalBlockRenderer(BlockEntityRendererFactory.Context ctx)
    {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        matrices.push();
        {
            MinecraftClient client = MinecraftClient.getInstance();
            String playerGuid = client.player.getUuidAsString();

            int c = blockEntity.customData.getOrDefault(playerGuid, 0);

            if (c != -1)
            {
                // Move the item
                renderKeys(blockEntity, tickDelta, matrices, vertexConsumers, light, overlay, c);
                matrices.translate(0.5, 1.25, 0.5);

                int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
                itemRenderer.renderItem(stack, ModelTransformationMode.HEAD, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            }

        }
        matrices.pop();
    }

    public void renderItem(PedestalBlockEntity blockEntity, ItemStack stack, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        renderItem(blockEntity, stack, tickDelta, matrices, vertexConsumers, light, overlay, 0.5, 0.5, 0);
    }

    public void renderItem(PedestalBlockEntity blockEntity, ItemStack stack, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, double x, double z, float dTheta)
    {
        matrices.push();
        {
            double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 8.0;

            // Move the item
            matrices.translate(x, 1.3 + offset, z);

            // Rotate the item
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((blockEntity.getWorld().getTime() + tickDelta) * 4 + (dTheta * 360)));

            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 0);
        }
        matrices.pop();
    }

    public void renderKeys(PedestalBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int c)
    {
        if (c < 1 || c > 4)
        {
            return;
        }

        matrices.push();
        {
            double x, z;
            float d;

            if (c == 4)
            {
                x = 0.2;
                z = 0.2;
                d = 0.25f;
            }
            else if (c == 3)
            {
                x = 0.2;
                z = 0.8;
                d = 0.5f;
            }
            else if (c == 2)
            {
                x = 0.8;
                z = 0.8;
                d = 0.75f;
            }
            else
            {
                assert(c == 1);
                x = 0.8;
                z = 0.2;
                d = 0.0f;
            }

            renderItem(blockEntity, new ItemStack(ModItems.RUBY_TRIAL_KEY), tickDelta, matrices, vertexConsumers, light, overlay, x, z, d);
        }
        matrices.pop();
    }
}
