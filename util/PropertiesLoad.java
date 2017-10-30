package util;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import model.dto.QuizProperties;

public class PropertiesLoad {
   
   public PropertiesLoad(){}
   public Map<String, QuizProperties> load(){
      Map<String, QuizProperties> quizMap = new TreeMap<>();
      Properties pro = new Properties();
      try {
    	  pro.load(new FileReader("C:/image/properties/quiz_table.properties"));
      }catch(Exception e) {
    	  e.printStackTrace();
      }

      Iterator<String> it = pro.stringPropertyNames().iterator();
      while(it.hasNext()){
         String key = it.next();
         String value = pro.getProperty(key);
         QuizProperties qp = new QuizProperties(Integer.parseInt(key), value);
         quizMap.put(key, qp);
      }
      return quizMap;
   }

}