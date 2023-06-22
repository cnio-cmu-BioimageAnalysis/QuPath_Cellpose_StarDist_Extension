import qupath.ext.stardist.StarDist2D

// Specify the model file (you will need to change this!)
// Get current image - assumed to have color deconvolution stains set
var imageData = getCurrentImageData()
var stains = imageData.getColorDeconvolutionStains()

// Set everything up with single-channel fluorescence model
var pathModel = '/path/to/dsb2018_heavy_augment.pb'

var stardist = StarDist2D.builder(pathModel)
        .preprocess( // Extra preprocessing steps, applied sequentially
            ImageOps.Channels.deconvolve(stains),
            ImageOps.Channels.extract(0),
            ImageOps.Filters.median(2),
            ImageOps.Core.divide(1.5)
         )
        .pixelSize(0.5)
        .includeProbability(true)
        .threshold(0.5)
        .build()
        
var imageData = getCurrentImageData()
var pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'