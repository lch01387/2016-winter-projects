#-*- coding:utf-8 -*-

import urllib
import lxml
from bs4 import BeautifulSoup

data = urllib.urlopen("http://eecs.kookmin.ac.kr/site/computer/notice.htm")
soup = BeautifulSoup(data, "lxml")
titles = soup.find_all('a', 'bd_subject')
# print soup
for title in titles :
    print title.text
for title in titles :
    print title['href']
print 'check'
titles[0]['href']
#for title in titles:
#	print(title.find('a', attrs={'class':''})
# <td class="title"> td태그의 class값이 title이다

#2.7.111
#decode('utf-8')