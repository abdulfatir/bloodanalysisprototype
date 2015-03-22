% Otsu's Segmentation
RGB = imread('test.jpg');
GRAY = rgb2gray(RGB);
T = graythresh(GRAY);
BINARY = im2bw(GRAY, T);
figure, imshow(GRAY);
figure, imshow(BINARY);
