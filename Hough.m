% Circle detection using Hough Transform
RGB = imread('test.jpg');
GRAY = rgb2gray(RGB);
EDGE = edge(GRAY,'canny');
figure, imshow(EDGE);
[centers, radii, metric] = imfindcircles(EDGE, [25 80]);
figure, imshow(GRAY);
hold on;
viscircles(centers, radii);
