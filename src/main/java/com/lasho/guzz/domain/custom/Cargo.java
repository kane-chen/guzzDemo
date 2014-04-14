package com.lasho.guzz.domain.custom;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cargo {
        
        private int id ;
        
        private String name ;
        
        private String description ;
        
        /**how many items left in the store. */
        private int storeCount ;
        
        private double price ;
        
        private Date onlineTime ;
        
        /**
         * special properties owned by this Item only.
         */
        private Map<String,Object> specialProps = new HashMap<String,Object>() ;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getStoreCount() {
			return storeCount;
		}

		public void setStoreCount(int storeCount) {
			this.storeCount = storeCount;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public Date getOnlineTime() {
			return onlineTime;
		}

		public void setOnlineTime(Date onlineTime) {
			this.onlineTime = onlineTime;
		}

		public Map<String,Object> getSpecialProps() {
			return specialProps;
		}

		public void setSpecialProps(Map<String,Object> specialProps) {
			this.specialProps = specialProps;
		}
        
        
        
}
