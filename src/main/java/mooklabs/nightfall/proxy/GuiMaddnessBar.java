package mooklabs.nightfall.proxy;

import java.text.DecimalFormat;

import mooklabs.nightfall.NFMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMaddnessBar extends Gui {

	private Minecraft mc;
	/* (my added notes:) ResourceLocation takes 2 arguments: your mod id and the path to your texture file, starting from the folder 'textures/' from
	 * '/src/minecraft/assets/yourmodid/', or you can write it all as a single argument. The texture file must be 256x256 (or multiples thereof) If you want a texture to test
	 * out the tutorial with, I've uploaded the mana_bar.png to my github page: https://github.com/coolAlias/Forge_Tutorials/tree/master/textures/gui */
	private static final ResourceLocation texturepath = new ResourceLocation(NFMain.modid, "textures/gui/insanity.png");

	public GuiMaddnessBar(Minecraft mc) {
		super();
		// We need this to invoke the render engine.
		this.mc = mc;
	}
	int bar;
	//
	// This event is called by GuiIngameForge during each frame by
	// GuiIngameForge.pre() and GuiIngameForce.post().
	//
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		//if(event.type == ElementType.EXPERIENCE)event.setCanceled(true);

		// We draw after the ExperienceBar has drawn. The event raised by GuiIngameForge.pre() will return true from isCancelable. If you call event.setCanceled(true) in that
		// case, the portion of rendering which this event represents will be canceled. We want to draw *after* the experience bar is drawn, so we make sure isCancelable()
		// returns false and that the eventType represents the ExperienceBar event.
		if (event.type != ElementType.EXPERIENCE)
			return;
		if(event.isCancelable())return;
		//event.setCanceled(true);//doesent acctually render exp

		int windowHeight = event.resolution.getScaledHeight();
		int windowWidth  = event.resolution.getScaledWidth();

		/** Start of my tutorial */

		// Get our extended player properties and assign it locally so we can easily access it

		EntityPlayer player = this.mc.thePlayer;
		ExtendedPlayer props = ExtendedPlayer.get(player);

		// Starting position for the mana bar - 2 pixels from the top left corner.
		int xPos = 2, yPos = 2;

		// The center of the screen can be gotten like this during this event:
		// int xPos = event.resolution.getScaledWidth() / 2;int yPos = event.resolution.getScaledHeight() / 2;

		// Be sure to offset based on your texture size or your texture will not be truly centered:
		// int xPos = (event.resolution.getScaledWidth() + textureWidth) / 2; int yPos = (event.resolution.getScaledHeight() + textureHeight) / 2;

		// setting all color values to 1.0F will render the texture as it looks in your texture file
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// Somewhere in Minecraft vanilla code it says to do this because of a lighting bug
		GL11.glDisable(GL11.GL_LIGHTING);

		// Bind your texture to the render engine
		this.mc.getTextureManager().bindTexture(texturepath);

		//put in text



		/* The parameters for drawTexturedModalRect are as follows: drawTexturedModalRect(int x, int y, int u, int v, int width, int height); x and y are the on-screen
		 * position at which to render. u and v are the bcoordinates of the most upper-left pixel in your texture file from which to start drawing. width and height are how
		 * many pixels to render from the start point (u, v) */
		// First draw the background layer. In my texture file, it starts at the upper-
		// left corner (x=0, y=0), ends at 50 pixels (so it's 51 pixels long) and is 3 pixels thick (y value)<---irrelivant now
		this.drawTexturedModalRect(xPos, windowHeight-7, 0, 3, 42, 5);

		int barheight = 50;
		this.drawTexturedModalRect(windowWidth/2-50, windowHeight-barheight, 0, 3, 42, 5);
		this.drawTexturedModalRect(windowWidth/2+8, windowHeight-barheight, 0, 3, 42, 5);
		barheight--;
		int aa = player.experienceLevel*2;if(aa>40)aa=40;
		int bb = (int)(player.experience/player.xpBarCap()*40);if(bb>40)bb=40;
		this.drawTexturedModalRect(windowWidth/2+1-50, windowHeight-barheight, 0, 0, aa, 3);
		this.drawTexturedModalRect(windowWidth/2+1+8, windowHeight-barheight, 0, 0, bb, 3);


		// Then draw the foreground; it's located just below the background in my
		// texture file, so it starts at x=0, y=4, is only 2 pixels thick and 49 length
		// Why y=4 and not y=5? Y starts at 0, so 0,1,2,3 = 4 pixels for the background

		// However, we want the length to be based on current insanity, so we need a new variable:
		int insanity = props.getInsanity();//changeMana(0);
		bar++;
		//System.out.println("[GUI MANA] Current mana bar width: " + player.cameraPitch+"    "+insanity);
		// Now we can draw our mana bar at yPos+1 so it centers in the background:
		this.drawTexturedModalRect(xPos+1, windowHeight-6, 0, 0, insanity, 3);
		//	System.out.println(insanity+"");
		//this.drawTexturedModalRect(xPos+1, windowHeight-50, 0, 0, bar, 3);
		//this.drawTexturedModalRect(xPos+1, windowHeight-40, 0, 0, props.maxInsanity, 3);

		if(bar>40)bar=0;
		//renders text to tell what stuff is, must be done last for some reason, if not last fuc*s everything gui-related up
		mc.fontRenderer.drawString("Insanity [example]"+insanity, 2, windowHeight-17, 1,false);

		DecimalFormat df = new DecimalFormat("#.##");

		mc.fontRenderer.drawString("Thirst: "+player.experienceLevel, 		windowWidth/2-50, windowHeight-60, 1, false);
		mc.fontRenderer.drawString("Vis: "+df.format(player.experience/player.xpBarCap()*40)+"BROKEN", 	windowWidth/2+10, windowHeight-60, 1, false);

	}



}