% Watershed Segmentation
RGB = imread('test.jpg');
GRAY = rgb2gray(RGB);
figure, imshow(GRAY);
T = graythresh(GRAY);
BINARY = im2bw(GRAY, T);
D=-bwdist(BINARY);
D(BINARY)=-Inf;
L=watershed(D);
OUT=label2rgb(L);
figure, imshow(OUT);
