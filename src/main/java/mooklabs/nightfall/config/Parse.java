package mooklabs.nightfall.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import mooklabs.nightfall.NFMain;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class Parse {

	public static void createItemFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("config/items.txt"), "utf-8"));
			writer.write("Name=Shell Sword\nLore=Sharpish\nDamage=5\nTexture=shellSword\nMaxDura=10\n--");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Item> readFromConfig() {
		String name = "", lore = "", texture = "";
		int maxDura = 20, dura = 20;
		float dam = 1;
		Item item = null;
		ArrayList<Item> items = new ArrayList();
		try {

			Scanner scanner = new Scanner(new File("config/items.txt"), "utf-8");
			ArrayList<String> array = new ArrayList();
			while (scanner.hasNextLine()) {

				// get and split strings
				while (scanner.hasNextLine()) {
					array.add(scanner.nextLine());
					if (array.get(array.size() - 1).equals("--")) break;
				}
				for (String s : array) {

					if (s.startsWith("Name")) name = s.replaceFirst("Name=", "").trim();
					if (s.startsWith("Lore")) lore = s.replaceFirst("Lore=", "").trim();
					if (s.startsWith("Dura")) dura = Integer.parseInt(s.replaceFirst("Dura=", "").trim());

					if (s.startsWith("Item")) item = GameRegistry.findItem("weaponmod", s.replaceFirst("Item=", "").trim());

					// only my items
					if (s.startsWith("Texture")) texture = s.replaceFirst("Texture=", "").trim();
					if (s.startsWith("Damage")) dam = Float.parseFloat(s.replaceFirst("Damage=", "").trim());
					if (s.startsWith("MaxDura")) maxDura = Integer.parseInt(s.replaceFirst("MaxDura=", "").trim());
				}

				// Double z = (double) Math.round(Double.parseDouble(""));
				if (item == null) items.add(new LootItem(name, lore, texture, dam - 4, maxDura - 1, dura));// have to subtract 4 from damage
				else {
					items.add(new ExtLootItem(item, name, lore, dura));
				}
				item = null;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			createItemFile();
		} catch (IOException e) {
			NFMain.instance.logger.fatal("File Read Error!");
			NFMain.instance.logger.fatal(e.getMessage());
			e.printStackTrace();

			NFMain.instance.logger.fatal("This means special items wont show up!");
			NFMain.instance.logger.fatal("Report as error immediatly!");
			System.exit(0);
		}

		System.err.println(items.size());
		return items;

	}
}
