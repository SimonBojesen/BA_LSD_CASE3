set -xe

if [ $TRAVIS_BRANCH == 'main' ] ; then
  eval "$(ssh-agent -s)"
  ssh-add ~/.ssh/id_rsa

#  rsync -a --exclude={'/src','/public'} client/ travis@207.154.224.203:/root/travis/demo/client
  rsync -a src/main/ travis@207.154.197.222:/root/travis/demo/server
else
  echo "Not deploying, since the branch isn't master."
fi