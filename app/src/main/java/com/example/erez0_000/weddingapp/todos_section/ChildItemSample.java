package com.example.erez0_000.weddingapp.todos_section;

public class ChildItemSample {
        private boolean checked;
        private String name;
        public boolean isChecked() {
            return checked;
        }
        public void setChecked(boolean checked) {
            this.checked = checked;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public ChildItemSample(){
            checked = false;
            name = "";
        }
        public ChildItemSample(String name){
            checked = false;
            this.name = name;
        }
    }

