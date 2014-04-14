package com.lasho.guzz.domain.custom;
public class SpecialProperty {

        /**unqiue id for management.*/
        private int id ;
        
        /**which cargo this property belongs to.*/
        private String cargoName ;
        
        /**property name of the cargo (used in java).*/
        private String propName ;
        
        /**the column name in the table of database to store this propety.*/
        private String colName ;
        
        /**
         * dataType. take this as the 'type' property in hbm.xml file.
         */
        private String dataType ;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCargoName() {
			return cargoName;
		}

		public void setCargoName(String cargoName) {
			this.cargoName = cargoName;
		}

		public String getPropName() {
			return propName;
		}

		public void setPropName(String propName) {
			this.propName = propName;
		}

		public String getColName() {
			return colName;
		}

		public void setColName(String colName) {
			this.colName = colName;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
        
        
        
}