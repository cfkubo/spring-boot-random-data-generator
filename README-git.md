
#### Working with git branches

> Check which branch is current

git checkout main
git pull origin main

> Create new feature branch

git checkout -b feature/testing-new-feature

> Commit the branch to git
git push -u origin feature/testing-new-feature

### Merging branches

>> Check which branch is current and switch to main

git checkout main
git pull origin main

> Merge the feature branch to main

git merge feature/testing-new-feature 

> Commit the changes to main branch 

git push origin main