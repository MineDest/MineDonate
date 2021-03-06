package ru.alastar.minedonate.merch.info;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.plugin.PluginHelper;

import java.io.UnsupportedEncodingException;

/**
 * Created by Alastar on 20.07.2017.
 */
public class RegionInfo extends Merch {

    public String world_name;
    public String name;

    public RegionInfo(int _shopId, int _catId, int mid, int cost, String name, String world_name) {
    	super(_shopId, _catId, mid);
        this.cost = cost;
        this.name = name;
        this.world_name = world_name;
    }


    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
    	
    	if ( PluginHelper . wgMgr . checkRegionMaxOut ( this . world_name, serverPlayer . getDisplayName ( ) ) ) {
 		 
    		serverPlayer . addChatMessage ( new ChatComponentText ( "You can't have more regions!" ) ) ;

    		return false ;
    		
    	} else {
    				   
    		return true;
               
    	}

    	/*
        try {       	
        	
            World bukkit_world = Bukkit.getWorld(this.world_name);
            Object plr = Bukkit.getPlayer(serverPlayer.getDisplayName());
            Object wg = MineDonate.wg_plugin.getClass().getMethod("inst").invoke(null);
            Object localPlayer = wg.getClass().getMethod("wrapPlayer", org.bukkit.entity.Player.class).invoke(wg, plr);
            Object reg_cont = wg.getClass().getMethod("getRegionContainer").invoke(wg);
            Object region_manager = reg_cont.getClass().getMethod("get", org.bukkit.World.class).invoke(reg_cont, bukkit_world);
            Object g_state_manager = wg.getClass().getMethod("getGlobalStateManager").invoke(wg);
            Object tmp = g_state_manager.getClass().getMethod("get", org.bukkit.World.class).invoke(g_state_manager, bukkit_world);
            Object resolver = tmp.getClass().getMethod("getMaxRegionCount", Player.class).invoke(tmp, plr);
            int res = (Integer) resolver;
            int plr_cnt = (Integer) region_manager.getClass().getMethod("getRegionCountOfPlayer", MineDonate.local_player_class).invoke(region_manager, localPlayer);
            System.out.println(res + " resolver, count " + plr_cnt);
            if (res >= 0 && plr_cnt >= res) {
                serverPlayer.addChatMessage(new ChatComponentText("You can't have more regions!"));
                return false;
            } else {
                return true;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
      //  serverPlayer.addChatMessage(new ChatComponentText("Errorrrrrrrr!"));
      //  return false;
    }

    @Override
    public int getAmountToBuy() {
        return 1;
    }

    public RegionInfo() {
    	super();
    }

    @Override
    public int getCategory() {
        return 2;
    }

    @Override
    public void read(ByteBuf buf) {
    	super.read(buf);
        cost = buf.readInt();
        int info_length = buf.readInt();
        try {
            this.name = new String(buf.readBytes(info_length).array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf buf) {
    	super.write(buf);
        buf.writeInt(cost);
        buf.writeInt(name.getBytes().length);
        buf.writeBytes(name.getBytes());
    }

    @Override
    public String getBoughtMessage() {
        return " bought region " + name + "=" + world_name;
    }
    
	@SideOnly(Side.CLIENT)
	@Override
	public String getSearchValue ( ) {
		
		return EnumChatFormatting . getTextWithoutFormattingCodes ( name + world_name ) ;
		
	}
	
}
