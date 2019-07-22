package gmail.fopypvp174.cmterrenos.api;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

public class ItemBuilder {

    private CmTerrenos plugin = CmTerrenos.getPlugin(CmTerrenos.class);

    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }


    public static ItemStack create(Material tipo, String nome, Enchantment encantamento, int level) {
        ItemStack item = new ItemStack(tipo, 1);
        ItemMeta m = item.getItemMeta();
        m.addEnchant(encantamento, level, true);
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack createSkull(String owner) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwner(owner);
        item.setItemMeta(skullMeta);
        return item;
    }

    public static ItemStack createSkull(String owner, String name) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        skullMeta.setOwner(owner);
        item.setItemMeta(skullMeta);
        return item;
    }

    public static ItemStack createSkull(String owner, String name, ArrayList<String> lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        skullMeta.setOwner(owner);
        for (int i = 0; i < lore.size(); i++) {
            lore.add(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
            lore.remove(i + 1);
        }
        skullMeta.setLore(lore);
        item.setItemMeta(skullMeta);
        return item;
    }

    public static ItemStack createSkull(String owner, ArrayList<String> lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwner(owner);
        for (int i = 0; i < lore.size(); i++) {
            lore.add(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
            lore.remove(i + 1);
        }
        skullMeta.setLore(lore);
        item.setItemMeta(skullMeta);
        return item;
    }


    public static ItemStack create(Material tipo, String nome) {
        ItemStack item = new ItemStack(tipo, 1);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack create(Material tipo, String nome, Integer quantidade) {
        ItemStack item = new ItemStack(tipo, quantidade);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack create(Material tipo, String nome, Integer quantidade, Byte data) {
        ItemStack item = new ItemStack(tipo, quantidade, (short) 0, data);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack create(Material tipo, String nome, Byte data) {
        ItemStack item = new ItemStack(tipo, 1, (short) 0, data);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack create(Material tipo, Byte data) {
        return new MaterialData(tipo, data).toItemStack();
    }

    public static ItemStack create(Material tipo, String nome, ArrayList<String> lore) {
        ItemStack item = new ItemStack(tipo, 1);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));

        for (int i = 0; i < lore.size(); i++) {
            lore.add(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
            lore.remove(i + 1);
        }
        m.setLore(lore);
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack create(Material tipo, String nome, ArrayList<String> lore, byte data) {
        ItemStack item = new ItemStack(tipo, 1, data);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        for (int i = 0; i < lore.size(); i++) {
            lore.add(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
            lore.remove(i + 1);
        }
        m.setLore(lore);
        item.setItemMeta(m);
        return item;
    }

    public static boolean hasEnchants(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        return nmsStack.hasEnchantments();
    }

}
