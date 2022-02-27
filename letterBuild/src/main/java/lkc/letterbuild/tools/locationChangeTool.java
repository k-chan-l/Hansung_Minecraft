package lkc.letterbuild.tools;

public class locationChangeTool {
    private int Xdirection;
    private int Ydirection;
    private int Zdirection;
    private boolean xFirst;
    private char row;
    private char column;
    private int Row;
    private int Column;

    public locationChangeTool (int[] direction, boolean Xfirst){
        Xdirection = direction[0];
        Ydirection = direction[1];
        Zdirection = direction[2];
        xFirst = Xfirst;
        if (Ydirection == 0){
            if(xFirst) {
                column = 'x';
                row = 'z';
            }
            else {
                column = 'z';
                row = 'x';
            }
        }
        else {
            if (xFirst) {
                column = 'x';
            }
            else {
                column = 'z';
            }
            row = 'y';
        }
    }

    public void updateRowAndColumn(int Row, int Column)
    {
        this.Row = Row;
        this.Column = Column;
    }

    public int addX(int X){
        if(isRow('x'))
            return X+Row*Xdirection;
        else if (isColumn('x'))
            return X+Column*Xdirection;
        else return X;
    }


    public int addY(int Y){
        if(isRow('y'))
            return Y+Row*Ydirection;
        else if (isColumn('y'))
            return Y+Column*Ydirection;
        else return Y;
    }

    public int addZ(int Z){
        if(isRow('z'))
            return Z+Row*Zdirection;
        else if (isColumn('z'))
            return Z+Column*Zdirection;
        else return Z;
    }
    public boolean isRow(char r){
        return row == r;
    }
    public boolean isColumn(char c){
        return column == c;
    }


    public int getXdirection() {
        return Xdirection;
    }

    public int getYdirection() {
        return Ydirection;
    }

    public int getZdirection() {
        return Zdirection;
    }

    public void fillBlock(int num){

    }

}
