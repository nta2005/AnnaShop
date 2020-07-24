var myaudio = new Audio('audio/chill2.mp3');
try {
    myaudio.play();
    myaudio.loop = true;
} catch (error) {
    console.log('error');
}
myaudio.play();
myaudio.loop = true;