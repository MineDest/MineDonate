package ru.alastar.minedonate.merch.info;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import ru.alastar.minedonate.merch.Merch;

public class ShopInfo extends Merch {
	
    public int merch_id ;
    public int shopId ;
    public String owner ;
    public String name ;
    
    public boolean isFreezed ;
    public String freezer ;
    public String freezReason ;
    public boolean canVisibleFreezedText ;
    
    public String moneyType ;
    
    public ShopInfo ( int _merch_id, int _shopId, String _owner, String _name, boolean _isFreezed, String _freezer, String _freezReason, boolean _canVisibleFreezedText, String _moneyType ) {
    	
       	super();
       	
        this.merch_id = _merch_id;
    	this.catId = 4;
       	shopId = _shopId;
       	
        this . owner = _owner ;
        this . name = _name ;
        
        this . isFreezed = _isFreezed ;
        this . freezer = _freezer ;
        this . freezReason = _freezReason ;
        this . canVisibleFreezedText = _canVisibleFreezedText ;
        
        this . moneyType = _moneyType ;
        
    }	

    public ShopInfo ( ) {
       	
    	super();
       	
    }

    @Override
    public int getCategory ( ) {
    	
        return 4 ;
        
    }

    @Override
    public void read ( ByteBuf buf ) {
    	
        try {
        	
        	merch_id = buf . readInt ( ) ;
            shopId = buf . readInt ( ) ;
            
            owner = readString ( buf ) ;          
            name = readString ( buf ) ;

            isFreezed = buf.readBoolean();
            canVisibleFreezedText = buf . readBoolean ( ) ;

            if ( isFreezed && canVisibleFreezedText ) {
                
            	freezer = readString ( buf ) ;          
            	freezReason = readString ( buf ) ;
                
            }
                        
            moneyType = readString ( buf ) ;
            
        } catch ( Exception ex ) {
        	
        	ex . printStackTrace ( ) ; 
        	
        }
        
    }

    
    @Override
    public void write ( ByteBuf buf ) {
    	
        buf . writeInt ( merch_id ) ;
        buf . writeInt ( shopId ) ;
        
        writeString ( buf, owner ) ;
        writeString ( buf, name ) ;

        buf . writeBoolean ( isFreezed ) ;
        buf . writeBoolean ( canVisibleFreezedText ) ;

        if ( isFreezed && canVisibleFreezedText ) {
        	
            writeString ( buf, freezer ) ;
            writeString ( buf, freezReason ) ;

        }
                
        writeString ( buf, moneyType ) ;
        
    }

    String readString ( ByteBuf buf ) throws UnsupportedEncodingException {
    	
        return new String ( buf . readBytes ( buf . readInt ( ) ) . array ( ), "UTF-8" ) ;
        
    }
     
    
    void writeString ( ByteBuf buf, String str ) {
    	
        buf.writeInt(str.getBytes().length);
        buf.writeBytes(str.getBytes());
        
    }
     
    @Override
    public String getBoughtMessage() {
        return "WTF?" ;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getId() {
        return merch_id;
    }

    @Override
    public boolean canBuy(EntityPlayerMP serverPlayer, int amount) {
        return false;
    }

    @Override
    public int getAmountToBuy() {
        return 0;
    }

    @Override
    public void setId(int i) {
        merch_id = i;
    }
        
	@Override
	public int getShopId ( ) {
		
		return 0 ;
		
	}

	@Override
	public void setShopId ( int i ) {
				
	}

	@Override
	public String getMoneyType ( ) {
		
		return null ;
		
	}

	@Override
	public int withdrawMoney(String buyer, int amount) {
		return -1 ;
	}
    
	@SideOnly(Side.CLIENT)
	@Override
	public String getSearchValue ( ) {
		
		return EnumChatFormatting . getTextWithoutFormattingCodes ( name + owner ) ;
		
	}

	public void cleanFreezVisibleData ( ) {
		
		freezer = "" ;
		freezReason = "" ;
		canVisibleFreezedText = false ;
		
	}
	
}