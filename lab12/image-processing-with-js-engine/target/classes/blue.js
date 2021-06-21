//https://stackoverflow.com/questions/39667665/rgb-image-filter-in-java
var BufferedImage = Java.type("java.awt.image.BufferedImage");
var Color = Java.type("java.awt.Color");

function process(imgObj) {

    var copy = makeCopy(imgObj);

    for(var x = 0; x < copy.getWidth(); x++){
        for(var y = 0; y < copy.getHeight(); y++){
            var rgb = copy.getRGB(x, y);
            var color = new Color(rgb);
            var blue = color.getBlue();
            var onlyBlue = new Color(0, 0, blue).getRGB();
            copy.setRGB(x, y, onlyBlue);

        }
    }

    return copy;
}

function makeCopy(imgObj) {
    var cm = imgObj.getColorModel();
    var isAlphaPremultiplied = cm.isAlphaPremultiplied();
    var raster = imgObj.copyData(null);
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
}