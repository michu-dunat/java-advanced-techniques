//https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java

var BufferedImage = Java.type("java.awt.image.BufferedImage");


function process(imgObj) {

    var copy = makeCopy(imgObj);

    for(var x = 0; x < copy.getWidth(); x++){
        for(var y = 0; y < copy.getHeight(); y++){
            var rgb = copy.getRGB(x, y);
            var r = (rgb >> 16) & 0xFF;
            var g = (rgb >> 8) & 0xFF;
            var b = (rgb & 0xFF);

            // Normalize and gamma correct:
            var rr = Math.pow(r / 255.0, 2.2);
            var gg = Math.pow(g / 255.0, 2.2);
            var bb = Math.pow(b / 255.0, 2.2);

            // Calculate luminance:
            var lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;

            // Gamma compand and rescale to byte range:
            var grayLevel = 255.0 * Math.pow(lum, 1.0 / 2.2);
            var gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
            copy.setRGB(x, y, gray);
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