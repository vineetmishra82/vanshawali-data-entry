const { ipcRenderer } = require('electron');

document.getElementById("adminBtn").addEventListener('click',() => {
   
    ipcRenderer.send('backToMainPage');

  });
