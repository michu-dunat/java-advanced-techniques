//https://github.com/praserocking/MedianFilter/blob/master/MedianFilter.java

var Color = Java.type("java.awt.Color");

function process(imgObj) {

    var copy = makeCopy(imgObj);
    
    var pixel= new Array(9);
    var R= new Array(9);
    var B= new Array(9);
    var G=new Array(9);

    for(var i=1;i<copy.getWidth()-1;i++) {
        for(var j=1;j<copy.getHeight()-1;j++) {
            pixel[0]=new Color(copy.getRGB(i-1,j-1));
            pixel[1]=new Color(copy.getRGB(i-1,j));
            pixel[2]=new Color(copy.getRGB(i-1,j+1));
            pixel[3]=new Color(copy.getRGB(i,j+1));
            pixel[4]=new Color(copy.getRGB(i+1,j+1));
            pixel[5]=new Color(copy.getRGB(i+1,j));
            pixel[6]=new Color(copy.getRGB(i+1,j-1));
            pixel[7]=new Color(copy.getRGB(i,j-1));
            pixel[8]=new Color(copy.getRGB(i,j));
            for(var k=0;k<9;k++){
                R[k]=pixel[k].getRed();
                B[k]=pixel[k].getBlue();
                G[k]=pixel[k].getGreen();
            }
            R.sort(compareNumbers);
            G.sort(compareNumbers);
            B.sort(compareNumbers);
            copy.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
        }
    }

    return copy;
}

function compareNumbers(a, b) {
    return a - b
}

function makeCopy(imgObj) {
    var cm = imgObj.getColorModel();
    var isAlphaPremultiplied = cm.isAlphaPremultiplied();
    var raster = imgObj.copyData(null);
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
}