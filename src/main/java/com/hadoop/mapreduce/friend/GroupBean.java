package com.hadoop.mapreduce.friend;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GroupBean implements Writable {

    private char ower;
    private String team;

    public GroupBean() {
    }

    public GroupBean(char ower, String team) {
        this.ower = ower;
        this.team = team;
    }

    public void setOwer(char ower) {
        this.ower = ower;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public char getOwer() {
        return ower;
    }

    public String getTeam() {
        return team;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeChar(ower);
        dataOutput.writeUTF(team);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        ower=dataInput.readChar();
        team=dataInput.readUTF();
    }

    @Override
    public String toString() {
        char[] chars = team.toCharArray();
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(ower+"-> ");
        int count=1;
        for (char member:chars){
            if(count>=chars.length) {
                stringBuffer.append(member);
            }else {
                stringBuffer.append(member + " ");
            }
            count++;
        }
        return stringBuffer.toString();
    }
}
