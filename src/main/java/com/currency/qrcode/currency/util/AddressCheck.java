package com.currency.qrcode.currency.util;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.web3j.utils.Numeric;

public class AddressCheck {

   public static boolean isETHValidAddress(String address){
       if( !address.startsWith("0x")){
           return false;
       }
       try {
           String cleanHexInput = Numeric.cleanHexPrefix(address);
           Numeric.toBigIntNoPrefix(cleanHexInput);
           return cleanHexInput.length() == 40;
       }catch (Exception e){
           return false;
       }
   }

   public static boolean isBTCValidAddress(String address){
       try {
           NetworkParameters networkParameters = MainNetParams.get();
           Address address1 = Address.fromBase58(networkParameters,address);
           if(address1 != null){
               return true;
           }
       }catch (Exception e){
           return false;
       }
       return false;
   }

   public static boolean isTRXValidAddress(String address){
       if(address.startsWith("T")){
           return true;
       }
       return false;
   }




}
