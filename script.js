menuBtn.onclick = () => navLinks.classList.toggle("active");

const words=["Frontend Developer","Web Developer","JavaScript Learner"];
let w=0,c=0,del=false;

(function type(){
  const word=words[w];
  typingText.textContent=word.substring(0,c);

  if(!del && c++===word.length){
    del=true;
    return setTimeout(type,900);
  }
  if(del && c--===0){
    del=false;
    w=(w+1)%words.length;
  }
  setTimeout(type,del?45:80);
})();

document.addEventListener("mousemove",e=>{
  const x=(innerWidth/2-e.clientX)/50;
  const y=(innerHeight/2-e.clientY)/60;
  previewTilt.style.transform=`rotate(9deg) translate(${x}px,${y}px)`;
});

contactForm.onsubmit=e=>{
  e.preventDefault();
  successMsg.textContent="✅ Thanks! Your message has been sent.";
  contactForm.reset();
  setTimeout(()=>successMsg.textContent="",2500);
};
