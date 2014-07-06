package mooklabs.nightfall.proxy;

import mooklabs.nightfall.NFMain;
import mooklabs.nightfall.items.NFGuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.NetworkRegistry;


public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() { // This is for rendering entities and so forth
		//  RenderingRegistry.registerEntityRenderingHandler(Ohmu.class, new RenderOhmu(new ModelOhmu(), 0.5f));
		MinecraftForge.EVENT_BUS.register(new ProjectZEventHandler());
		MinecraftForge.EVENT_BUS.register(new GuiMaddnessBar(Minecraft.getMinecraft()));
		NetworkRegistry.INSTANCE.registerGuiHandler(NFMain.instance, new NFGuiHandler());
	}

}