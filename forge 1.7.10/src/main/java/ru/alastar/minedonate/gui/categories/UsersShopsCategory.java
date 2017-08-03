package ru.alastar.minedonate.gui.categories;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.CountButton;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.info.ItemInfo;
import ru.alastar.minedonate.merch.info.UserShopInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfItemMerch;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfUserShopMerch;

public class UsersShopsCategory implements ShopCategory {

	int catId = 4 ;
	ShopCategory userSC ;
	public int selectedShop = 0 ;
	
    @Override
    public boolean getEnabled() {
    	
        return ( userSC != null ? userSC.getEnabled() : MineDonate.cfg.userShops ) ;
        
    }

    @Override
    public int getSourceCount(int shopId) {
        
    	return ( userSC != null ? userSC.getSourceCount(shopId) : MineDonate . shops . containsKey ( shopId ) ? MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( ) . length : 0 ) ;
        
    }

    @Override
    public String getName() {
        
    	return "Users shops";
        
    }

    ScaledResolution resolution;
    
    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks, DrawType dt) {
    	
        resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
        if ( userSC != null && userSC . getScrollList ( ) != null && dt != DrawType . BG && dt != DrawType . POST ) { userSC . getScrollList ( ) . drawScreen(mouseX, mouseY, partialTicks, dt); }
    	gi.drawScreen(mouseX, mouseY, partialTicks, dt);
    	
    }

    @Override
    public void updateButtons(ShopGUI relative, int m_Page) {
    	
    	refreshGui ( ) ;
    	
    	if ( userSC != null ) {
    		
    		userSC . initGui (  ) ;
    		
    	}
    	
    }

    @Override
    public int elements_per_page() {
        return 0 ;
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
        
    	if ( userSC != null ) {
    		
    		userSC . actionPerformed ( button ) ;
    		
    	}
        
    }

    // #LOG
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.usersShops.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.usersShops.categoryButtonText ;
		
	}
	
	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public void setRowCount(int i) {
	}

	@Override
	public int getColCount() {
		return 0;
	}

	@Override
	public void setColCount(int i) {
	}

	@Override
	public int getItemWidth() {
		return 0;
	}

	@Override
	public int getItemHeight() {	
		return 0;	
	}

	GuiItemsScrollArea gi ;
	List < GuiAbstractItemEntry > entrs = new ArrayList < GuiAbstractItemEntry> ( ) ;
	
	ShopGUI gui ;
	
	@Override
	public void init ( ShopGUI _shopGUI ) {

		gui = _shopGUI ;
		
	}

	UserShopInfo iim ;
	
	@Override
	public void initGui ( ) {
	
		refreshGui ( ) ;
        if ( userSC != null ) { userSC . init ( gui ) ; userSC . initGui ( ) ; }

	}
	
	public void refreshGui ( ) {
		
		resolution = new ScaledResolution(gui.mc, gui.mc.displayWidth, gui.mc.displayHeight);

		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( userSC == null ) {
						
			if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
				
		    	if ( search ) {
		    		
		    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
		        		
		        		iim = ( UserShopInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
		        		
		        		if ( iim != null ) {

			        		if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . usersShops . dontShowFreezed : true ) && ( iim . owner . toLowerCase ( ) . contains ( searchValue ) ||  iim . name . toLowerCase ( ) . contains ( searchValue ) ) ) {
			        			
			        			entrs . add ( new GuiItemEntryOfUserShopMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
			        			
			        		}
			        		
		        		}
		        		
		        	} 
		        		
		    	} else {
		    		
		    		for ( int i = 0 ; i < MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) . length ; i ++ ) {
		        		
		        		iim = ( UserShopInfo ) MineDonate . shops . get ( gui . getCurrentShopId ( ) ) . cats [ catId ] . getMerch ( ) [ i ] ; 
		        		
		        		if ( iim != null ) {
		        		
		        			if ( ( iim . isFreezed ? ! MineDonate . cfgUI . cats . usersShops . dontShowFreezed : true ) ) {
		        			
		        				entrs . add ( new GuiItemEntryOfUserShopMerch ( iim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
		        			
		        			}
		        			
		        		}
		        		
		        	} 
		    		
		    	}
	    	}
		
		}
    	
    	gi . entrs = entrs ;
    	gi . applyScrollLimits ( ) ;
    	
	}
	
	public void updateUserShopCategory ( ShopCategory sc, boolean r ) {

		userSC = sc ;
		if ( r ) { refreshGui ( ) ; }
		
	}

	boolean search = false ;
	String searchValue = "" ;
	
	@Override
	public void search ( String text ) {
		
		if ( userSC != null ) { 
			
			userSC . search ( text ) ;
			return ;
			
		}
			
		search = ! ( text == null || text . trim ( ) . isEmpty ( ) ) ;
		
		if ( search ) {
			
			searchValue = text . toLowerCase ( ) . trim ( ) ;
			
		} else {
			
			searchValue = "" ;
			
		}
			
		refreshGui ( ) ;
	
		
	}

	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi;
		
	}

}
