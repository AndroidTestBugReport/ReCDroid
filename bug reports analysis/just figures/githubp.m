h1=figure('Position',[100 100 600 470]);
github=[62 5 52 69 1 2 15]
bar(github)
set(gca,'XTick',1:1:7,'XTickLabel',{'1','2','3','4','5','6','7'},'fontsize',12);
xlabel('CR Pattern Type','fontsize',24);
ylabel('#Bug Reports','fontsize',24);
title('github click pattern')


h2=figure('Position',[100 100 800 470])
github=[34 9 3 69 5 2 2 11 0 2 0 0 55 38]
bar(github)
set(gca,'XTick',1:1:14,'XTickLabel',{'1','2','3','4','5','6','7','8','9','10','11','12','13','14'},'fontsize',12);
xlabel('TR Pattern Type','fontsize',24);
ylabel('#Bug Reports','fontsize',24);
title('github type pattern')


h3=figure('Position',[100 100 600 470]);
googlecode=[40 1 18 47 0 1 13]
bar(googlecode)
set(gca,'XTick',1:1:7,'XTickLabel',{'1','2','3','4','5','6','7'},'fontsize',12);
xlabel('CR Pattern Type','fontsize',24);
ylabel('#Bug Reports','fontsize',24);
title('google code click pattern')

h4=figure('Position',[100 100 800 470])
googlecode=[25 6 1 8 9 1 1 1 1 0 0 0 41 36]
bar(googlecode)
set(gca,'XTick',1:1:14,'XTickLabel',{'1','2','3','4','5','6','7','8','9','10','11','12','13','14'},'fontsize',12);
xlabel('TR Pattern Type','fontsize',24);
ylabel('#Bug Reports','fontsize',24);
title('google code type pattern')


h5=figure('Position',[100 100 800 470])
github=[80 11 2 11 9 4 51 22 18 5 2 40 52 9 13 6 15 24 1]
bar(github)
set(gca,'XTick',1:1:19,'XTickLabel',{'1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19'},'fontsize',12);
xlabel('keyword','fontsize',24);
ylabel('#Bug Reports','fontsize',24);
title('github keyword')

h5=figure('Position',[100 100 800 470])
github=[74 4 4 19 9 1 39 15 13 6 2 34 40 7 16 4 7 9 2]
bar(github)
set(gca,'XTick',1:1:19,'XTickLabel',{'1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19'},'fontsize',12);
xlabel('keyword','fontsize',24);
ylabel('#Bug Reports','fontsize',24);
title('google code keyword')
