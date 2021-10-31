package eu.pb4.polymer.impl.compat;

import fr.catcore.server.translations.api.LocalizationTarget;
import fr.catcore.server.translations.api.nbt.StackNbtLocalizer;
import fr.catcore.server.translations.api.text.LocalizableText;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ServerTranslationUtils {
    public static final boolean IS_PRESENT = FabricLoader.getInstance().isModLoaded("server_translations_api");

    public static Text parseFor(ServerPlayNetworkHandler handler, Text text) {
        if (IS_PRESENT) {
            return LocalizableText.asLocalizedFor(text, (LocalizationTarget) handler.player);
        } else {
            return text;
        }
    }

    public static ItemStack parseFor(ServerPlayNetworkHandler handler, ItemStack stack) {
        if (IS_PRESENT) {
            var newStack = stack.copy();
            var newNbt = StackNbtLocalizer.localize(stack, stack.getNbt(), (LocalizationTarget) handler.player);
            newStack.setNbt(newNbt);
            return newStack;
        }
        return stack;
    }

    public static NbtCompound parseForNbt(ServerPlayNetworkHandler handler, ItemStack stack) {
        if (IS_PRESENT) {
            return StackNbtLocalizer.localize(stack, stack.getNbt(), (LocalizationTarget) handler.player);
        }
        return stack.getNbt();
    }
}
