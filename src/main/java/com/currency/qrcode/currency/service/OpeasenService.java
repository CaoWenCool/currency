package com.currency.qrcode.currency.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.currency.qrcode.currency.mapper.opensea.mapper.OpeasenMapper;
import com.currency.qrcode.currency.mapper.opensea.model.OpeasenPO;
import com.currency.qrcode.currency.model.OpeasenAttributeEnum;
import com.currency.qrcode.currency.util.JsonConvertUtil;
import com.currency.qrcode.currency.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class OpeasenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${opensea.init.local.path.url:/usr/local/workplace/BBNFT_JSON}")
    private String localPath;

    @Autowired
    OpeasenMapper opeasenMapper;

    public void insert(OpeasenPO opeasenPO){
        opeasenMapper.insert(opeasenPO);
    }

    public void init(){
        File file = new File(localPath);
        logger.info("request info:" +localPath);
        String[] fileList = file.list();
        StringBuffer sb = new StringBuffer();
        sb.append(TimeUtils.getMonthOfFirstDay());
        sb.append("-");
        sb.append(TimeUtils.getMonthOfLastOneDay());
        String taskId = sb.toString();
        for (int i=0;i<fileList.length;i++){
            if(!fileList[i].contains(".json")){
                continue;
            }
            logger.info("fileList[i] info:" +fileList[i]);
            String filePath = localPath + "/" + fileList[i];
            String eachFileContent = readEachFile(filePath);
            logger.info("eachFileContent info:" +eachFileContent);
            JSONObject jsonObject = JsonConvertUtil.fromJSON(eachFileContent, JSONObject.class);
            OpeasenPO opeasenPO = new OpeasenPO();
            opeasenPO.setImageName(jsonObject.getString("name"));
            opeasenPO.setDescription(jsonObject.getString("description"));
            opeasenPO.setEdition(jsonObject.getInteger("edition"));
            opeasenPO.setCreateDate(jsonObject.getString("date"));
            opeasenPO.setComplier(jsonObject.getString("compiler"));
            opeasenPO.setImage(jsonObject.getString("image"));
            opeasenPO.setTaskId(taskId);
            JSONArray jsonArray = jsonObject.getJSONArray("attributes");
            for(int j=0;j<jsonArray.size();j++){
                JSONObject eachAttribute = jsonArray.getJSONObject(j);
                String type = eachAttribute.getString("trait_type");
                OpeasenAttributeEnum opeasenAttributeEnum = OpeasenAttributeEnum.getOpeasenAttributeEnum(type);
                switch (opeasenAttributeEnum){
                    case Close:
                        opeasenPO.setPrice(eachAttribute.getInteger("value"));
                        break;
                    case HIGH:
                        opeasenPO.setHighPrice(eachAttribute.getInteger("value"));
                        break;
                    case LOW:
                        opeasenPO.setLowPrice(eachAttribute.getInteger("value"));
                        break;
                    case COLOR:
                        opeasenPO.setColor(eachAttribute.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            insert(opeasenPO);
        }
    }

    public String readEachFile(String filePath){
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                stringBuffer.append(line);
                line = bufferedReader.readLine();
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
