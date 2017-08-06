package com.five.high.emirim.geulgil.Model;

/**
 * Created by 두리 on 2017-07-26.
 */

public class Sense {
    String sense_no;
    String definition;
    String pos;
    String link;
    String type;
    String cat;
    public int choice=0;

    public Sense(){
    }

    public Sense(String sense_no, String definition, String pos, String link, String type, String cat) {
        this.sense_no = sense_no;
        this.definition = definition;
        this.pos = pos;
        this.link = link;
        this.type = type;
        this.cat = cat;
    }


    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSense_no() {
        return sense_no;
    }

    public void setSense_no(String sense_no) {
        this.sense_no = sense_no;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
