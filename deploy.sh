set -xe

if [ $TRAVIS_BRANCH == 'main' ] ; then
  eval "$(ssh-agent -s)"
  ssh-add ~/.ssh/id_rsa

#  rsync -a --exclude={'/src','/public'} client/ travis@207.154.224.203:/root/travis/demo/client
  scp booking-service.service root@207.154.197.222:/etc/systemd/system
  scp target/BA_LSD_CASE3-1.0-SNAPSHOT.jar root@207.154.197.222:/artifact
else
  echo "Not deploying, since the branch isn't master."
fi