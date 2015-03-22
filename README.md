# bloodanalysisprototype

Simple example/prototype programs for GSoC project by Helikar Lab - Mobile-Based Blood-Sample Image Analysis software.

This repo currently has:

a) MedianFilter.java - A sample implementation of the classic median filter which can be used in step 2 for image pre-processing/noise reduction.  
b) IntensityMap.java - Though the project has to be accomplished in 3D (Calculating volumes) this program converts image to a colored intensity map in 2D.  
c) OtsuSegmentation.java - An implementation of Otsu's algorithm to find the threshold for segmenting an image into a binary image.  
d) Convolution.java - An implementation of Kernel matrix convolution which can be used for different jobs like - blurring, mean filtering, edge detection (sobel etc.)  
e) Hough.m - A MATLAB demonstration of usage of Hough Transform for detection of circular areas in image. It uses Canny Edge detection as its first step.  
f) Otsu.m - A MATLAB demonstration of Otsu Segmentation.  
g) Watershed_Seg.m - A MATLAB demonstration of watershed algorithm for segmentation of image and pattern recognition.
