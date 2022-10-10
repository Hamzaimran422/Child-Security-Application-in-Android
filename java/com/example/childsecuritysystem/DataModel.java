package com.example.childsecuritysystem;

public class DataModel {
    public int U_ID , F_ID;
    public String U_Name,U_Email,Password,Role,C_Phone_No, F_Name ,FenceStatus;
    public String Gender;
    public  int ParentId;
        String name;
        String type;
        String version_number;
        String feature;
        public DataModel(){}

        public DataModel(String name, String type, String version_number, String feature ) {
            this.name=name;
            this.type=type;
            this.version_number=version_number;
            this.feature=feature;

        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getVersion_number() {
            return version_number;
        }

        public String getFeature() {
            return feature;
        }

    }


