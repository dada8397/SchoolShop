# SchoolShop

* 專為大專院校打造的...蝦皮 / PCHome / 奇摩拍賣 / 淘寶
* 同校、跨校間的交易分開
* 不處理金流，一切交易買賣雙方負責
* 伺服器留下對話記錄，協助提供紀錄處理糾紛，交易有保障
* 還有很多 feature...正在想 XD

You can download our apk in [here](https://github.com/dada8397/SchoolShop/releases)

## Library & Tool

* Django for backend
* Sqlite3 for database
* OkHttp for api request and response
* Picasso for load image

## Contributions of each team member

* 詹欣達（N26060258）20%：後端與商品列表
* 謝姍倪（N26070279）20%：刊登商品頁面
* 張維倫（N26061597）20%：商品列表
* 王康霖（N26077124）20%：商品詳細頁面
* 沈柏妤（N26070130）20%：聊天頁面

## Developer guideline

### Git Flow

[Git Flow 教學](https://ihower.tw/blog/archives/5140)

#### New branch

```
git pull origin master
git branch {your_name}
git checkout {your_name}
```

#### Merge and push project

```
git fetch --all
git merge origin/master
# Fix conflict (if have any), if you don't know how to fix it, contact dada
git add {your_files}
git commit
git push origin {your_name}
```
1. Go to [github page](https://github.com/dada8397/SchoolShop).
![Github Page](./Screenshots/01.png)
2. Select "Compare & pull request".
![Github Page](./Screenshots/02.png)
3. Select Assignee to dada8397.
4. Press "Create pull request".
5. Contact dada and wait for merge.

#### Test project

1. You can either `git merge origin/master` on your branch to test
2. Or you can checkout to master and `git pull origin master` to test
