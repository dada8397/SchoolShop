# SchoolShop

A final project for AAD course...
You can download our apk in [here](https://github.com/dada8397/SchoolShop/releases)

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
