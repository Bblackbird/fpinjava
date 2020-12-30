package com.funk.fsm;

//import lombok.Builder;

import com.funk.common.Result;

//@Builder
public class Leg {

    String legSecurityID;
    String legSymbol;
    String legSecurityType;
    //@Builder.Default
    Result<String> legSecurityIDSource = Result.empty();
    //@Builder.Default
    Result<String> legSecuritySubType = Result.empty();
    //@Builder.Default
    Result<String> legCTDSecurityID = Result.empty();
    //@Builder.Default
    Result<String> legCTDRank = Result.empty();
    //@Builder.Default
    Result<String> legSecurityXmlLen = Result.empty();
    //@Builder.Default
    Result<String> legSecurityXml = Result.empty();
    //@Builder.Default
    Result<String> legSecurityXmlSchema = Result.empty();

}
