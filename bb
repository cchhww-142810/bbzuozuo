<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>今天是在一起的第几天（请bb猪在下面输入）</title>
<style>
  :root{
    --violet:#8a5cff; --violet-dark:#6b3dff;
    --bg1:#faf6ff; --bg2:#f6f0ff; --text:#392b57;
    --pink:#ff4d6d; --pink-dark:#ff7aa2;
    --orange:#ff7f50; --purple:#a78bfa;
  }
  *{box-sizing:border-box}
  body{
    margin:0; min-height:100vh; display:flex; align-items:center; justify-content:center;
    font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","Helvetica Neue",Arial,sans-serif;
    color:var(--text); background:radial-gradient(1200px 800px at 50% -200px,#ffffff 0%,var(--bg1) 55%,var(--bg2) 100%); overflow:hidden;
  }

  /* 背景彩纸屑（心/涡线/点） */
  .confetti-layer{ position:fixed; inset:0; pointer-events:none; z-index:5; overflow:hidden; }
  .confetti{ position:absolute; will-change:transform, opacity; opacity:.95; }
  .c-heart, .c-squiggle, .c-dot{ display:block; }
  @keyframes confettiFall {
    0%   { transform: translate3d(var(--x), -10vh, 0) rotate(0deg) scale(var(--s,1)); opacity:0; }
    10%  { opacity:1; }
    100% { transform: translate3d(var(--x2), 110vh, 0) rotate(360deg) scale(var(--s,1)); opacity:1; }
  }

  .wrap{ width:min(96vw,760px); display:flex; flex-direction:column; align-items:center; gap:18px; }
  .hero{
    width:min(72vw, 380px); aspect-ratio:1/1; position:relative; z-index:12;
    filter: drop-shadow(0 18px 40px rgba(124,58,237,.18));
  }

  /* 卡片 */
  .card{
    width:min(94vw, 720px); background:#fff; border-radius:24px;
    box-shadow:0 16px 48px rgba(72,39,133,.14);
    padding:30px 28px 28px; text-align:center; position:relative; z-index:10;
  }
  h1{ margin:0 0 12px; white-space:nowrap; font-size:clamp(16px,3.8vw,24px); }
  .row{ display:flex; gap:12px; margin-top:12px; }
  input[type="number"]{ flex:1; padding:14px 16px; border-radius:14px; border:2px solid #d8ccff; outline:none; font-size:18px; }
  button{
    padding:14px 18px; border:none; border-radius:14px;
    background:linear-gradient(135deg,var(--violet),var(--violet-dark));
    color:#fff; font-size:16px; cursor:pointer;
  }
  .tip{ margin-top:10px; font-size:14px; color:#7a6aa8; min-height:1.2em; visibility:hidden; }
  .tip.show{ visibility:visible; }
  .err{ color:#7c3aed; height:1.2em; margin-top:4px; font-size:14px; }

  /* 粉红弹心 + 爱心雨 */
  .pop-heart{
    position:fixed; top:50%; left:50%; transform: translate(-50%,-50%) rotate(-45deg) scale(0);
    width:110px; height:110px; background: var(--pink); border-radius:12px; z-index:130;
    animation: pop .9s ease-out forwards;
    filter: drop-shadow(0 10px 25px rgba(255,77,109,.35));
  }
  .pop-heart::before,.pop-heart::after{ content:""; position:absolute; width:110px; height:110px; background:inherit; border-radius:50%; }
  .pop-heart::before{ top:-55px; left:0; } .pop-heart::after{ top:0; left:55px; }
  @keyframes pop{ 0%{ transform: translate(-50%,-50%) rotate(-45deg) scale(0); opacity:0; }
                  55%{ transform: translate(-50%,-50%) rotate(-45deg) scale(1.15); opacity:1; }
                  100%{ transform: translate(-50%,-50%) rotate(-45deg) scale(1); opacity:1; } }
  #hearts{ position:fixed; inset:0; pointer-events:none; z-index:50; }
  .heart{
    position:absolute; width:22px; height:22px; background: var(--pink);
    transform: rotate(-45deg); border-radius:4px; animation: fall 7s linear forwards;
    filter: drop-shadow(0 4px 10px rgba(255,77,109,.25));
  }
  .heart::before,.heart::after{ content:""; position:absolute; width:22px; height:22px; background: inherit; border-radius:50%; }
  .heart::before{ top:-11px; left:0; } .heart::after{ top:0; left:11px; }
  @keyframes fall{ 0%{ transform: translateY(-12vh) rotate(0deg); opacity:0; }
                   5%{ opacity:.95; } 100%{ transform: translateY(112vh) rotate(360deg); opacity:.95; } }

  /* 大字 SVG 弹窗（居中 + 自动缩放） */
  .svg-pop {
    position: fixed; top: 50%; left: 50%; transform: translate(-50%,-50%) scale(.2);
    z-index: 140; width: 90vw; max-width: 1100px; height: 35vh; opacity:0;
    animation: svgPopIn 1.2s cubic-bezier(.16,.9,.2,1) forwards;
  }
  @keyframes svgPopIn { 0% { transform: translate(-50%,-50%) scale(.2); opacity:0; }
                        55%{ transform: translate(-50%,-50%) scale(1.12); opacity:1; }
                        100%{ transform: translate(-50%,-50%) scale(1); opacity:1; } }
  .svg-pop.fade-out { animation: svgPopOut .6s ease forwards; }
  @keyframes svgPopOut { to { transform: translate(-50%,-50%) scale(.98); opacity:0; } }
</style>
</head>
<body>
  <!-- 背景纸屑层 -->
  <div class="confetti-layer" id="confetti"></div>

  <div class="wrap">
    <!-- 情人节风格徽章（受你图案启发的礼物+爱心造型，非品牌Logo） -->
    <svg class="hero" viewBox="0 0 400 400" aria-hidden="true">
      <defs>
        <linearGradient id="g1" x1="0%" y1="0%" x2="100%" y2="0%">
          <stop offset="0%"  stop-color="#ff7aa2"/>
          <stop offset="50%" stop-color="#ff4d6d"/>
          <stop offset="100%" stop-color="#a78bfa"/>
        </linearGradient>
      </defs>
      <!-- 外圈圆形礼盒轮廓 -->
      <circle cx="200" cy="200" r="150" fill="none" stroke="url(#g1)" stroke-width="20" stroke-linecap="round" />
      <!-- 礼盒十字丝带 -->
      <line x1="200" y1="70" x2="200" y2="330" stroke="url(#g1)" stroke-width="20" stroke-linecap="round"/>
      <line x1="70" y1="200" x2="330" y2="200" stroke="url(#g1)" stroke-width="20" stroke-linecap="round"/>
      <!-- 顶部爱心蝴蝶结 -->
      <path d="M200 120
               c-26 -40 -80 -35 -90 0
               c-8 30 20 55 90 90
               c70 -35 98 -60 90 -90
               c-10 -35 -64 -40 -90 0 Z"
            fill="none" stroke="url(#g1)" stroke-width="20" stroke-linecap="round" stroke-linejoin="round"/>
      <!-- 内部俏皮线条 -->
      <path d="M125 245 q25 25 0 50" fill="none" stroke="url(#g1)" stroke-width="18" stroke-linecap="round"/>
      <path d="M165 285 q30 20 0 40" fill="none" stroke="url(#g1)" stroke-width="18" stroke-linecap="round"/>
      <path d="M240 245 q-25 25 0 50" fill="none" stroke="url(#g1)" stroke-width="18" stroke-linecap="round"/>
      <circle cx="280" cy="285" r="12" fill="url(#g1)"/>
      <circle cx="120" cy="165" r="12" fill="url(#g1)"/>
    </svg>

    <!-- 你的卡片（输入+按钮） -->
    <div class="card" id="card">
      <h1>今天是在一起的第几天（请bb猪在下面输入）</h1>
      <div class="row">
        <input id="days" type="number" inputmode="numeric" />
        <button id="okBtn">确认</button>
      </div>
      <div class="tip" id="tip">bb猪~四位数哟</div>
      <div class="err" id="err"></div>
    </div>
  </div>

  <!-- 持续爱心雨层 -->
  <div id="hearts"></div>

<script>
(function(){
  const CORRECT = 1000;                         // 正确答案
  const SUCCESS_MSG1 = "厉害猪猪！！奖励抱抱亲亲！！"; // 先出现（紫色），短暂
  const SUCCESS_MSG2 = "bb猪我爱你哟！！！";          // 后出现（粉色），常驻
  const ERROR_POP    = "不对哟～再试试~";            // 错误时

  const input = document.getElementById('days');
  const btn   = document.getElementById('okBtn');
  const tip   = document.getElementById('tip');
  const card  = document.getElementById('card');
  const heartsLayer = document.getElementById('hearts');

  /* ---------- 背景彩纸屑（心/涡线/点） ---------- */
  const confettiLayer = document.getElementById('confetti');
  const palette = ['#ff7aa2','#ff4d6d','#ff7f50','#a78bfa','#6b3dff'];
  function spawnConfetti(){
    const el = document.createElement('div');
    el.className='confetti';
    const type = Math.random();
    const color = palette[Math.floor(Math.random()*palette.length)];
    const x = Math.random()*100;           // 起点（vw）
    const x2= x + (Math.random()*20-10);   // 终点偏移
    const s = 0.6 + Math.random()*1.2;     // 缩放
    const dur = 7 + Math.random()*7;       // 时长

    el.style.setProperty('--x', x+'vw');
    el.style.setProperty('--x2', x2+'vw');
    el.style.setProperty('--s', s.toFixed(2));
    el.style.animation = 'confettiFall '+dur+'s linear forwards';
    el.style.top = '-8vh';

    if(type < 0.45){
      // 爱心
      const svgNS="http://www.w3.org/2000/svg";
      const svg=document.createElementNS(svgNS,'svg');
      svg.setAttribute('class','c-heart');
      svg.setAttribute('width', 26); svg.setAttribute('height', 26);
      svg.setAttribute('viewBox','0 0 24 24');
      const g=document.createElementNS(svgNS,'g');
      g.setAttribute('transform','translate(12,12) rotate(-45)');
      const rect=document.createElementNS(svgNS,'rect');
      rect.setAttribute('x','-6'); rect.setAttribute('y','-6');
      rect.setAttribute('width','12'); rect.setAttribute('height','12');
      rect.setAttribute('rx','3'); rect.setAttribute('fill', color);
      const c1=document.createElementNS(svgNS,'circle');
      c1.setAttribute('cx','0'); c1.setAttribute('cy','-6');
      c1.setAttribute('r','6'); c1.setAttribute('fill',color);
      const c2=document.createElementNS(svgNS,'circle');
      c2.setAttribute('cx','6'); c2.setAttribute('cy','0');
      c2.setAttribute('r','6'); c2.setAttribute('fill',color);
      g.appendChild(rect); g.appendChild(c1); g.appendChild(c2);
      svg.appendChild(g); el.appendChild(svg);
    }else if(type < 0.8){
      // 丝带弯弯
      const svgNS="http://www.w3.org/2000/svg";
      const svg=document.createElementNS(svgNS,'svg');
      svg.setAttribute('class','c-squiggle');
      svg.setAttribute('width', 30); svg.setAttribute('height', 30);
      svg.setAttribute('viewBox','0 0 24 24');
      const path=document.createElementNS(svgNS,'path');
      path.setAttribute('d','M2 12 q6 -8 12 0 q6 8 12 0');
      path.setAttribute('fill','none');
      path.setAttribute('stroke', color);
      path.setAttribute('stroke-width','3.2');
      path.setAttribute('stroke-linecap','round');
      path.setAttribute('stroke-linejoin','round');
      svg.appendChild(path); el.appendChild(svg);
    }else{
      // 小圆点
      const dot=document.createElement('span');
      dot.className='c-dot';
      dot.style.display='block';
      dot.style.width = dot.style.height = (6+Math.random()*6)+'px';
      dot.style.background=color; dot.style.borderRadius='50%';
      el.appendChild(dot);
    }

    confettiLayer.appendChild(el);
    setTimeout(()=> el.remove(), dur*1000 + 500);
  }
  // 持续生成纸屑
  setInterval(spawnConfetti, 220);
  for(let i=0;i<20;i++) setTimeout(spawnConfetti, i*80);

  /* ---------- 粉色爱心雨（一直落） ---------- */
  function popHeart(){
    const h = document.createElement('div');
    h.className = 'pop-heart';
    document.body.appendChild(h);
    h.addEventListener('animationend', () => h.remove(), {once:true});
  }
  function makeFallingHeart(){
    const h = document.createElement('div');
    h.className = 'heart';
    h.style.left = Math.random()*100 + 'vw';
    h.style.top  = (-10 - Math.random()*15) + 'vh';
    heartsLayer.appendChild(h);
    h.addEventListener('animationend', () => h.remove());
  }
  function startHeartRainForever(){
    for (let i=0;i<16;i++) makeFallingHeart();
    setInterval(makeFallingHeart, 200);
  }

  /* ---------- SVG 弹字：自动缩放 + 居中 ---------- */
  function svgPopText(text, mode='pink', hold=1500, permanent=false){
    const svgNS = "http://www.w3.org/2000/svg";
    const svg   = document.createElementNS(svgNS, "svg");
    svg.setAttribute("class", "svg-pop");
    svg.setAttribute("viewBox", "0 0 1000 400");
    svg.setAttribute("preserveAspectRatio", "xMidYMid meet");

    const defs = document.createElementNS(svgNS, "defs");
    const grad = document.createElementNS(svgNS, "linearGradient");
    const gradId = "grad_" + Math.random().toString(36).slice(2);
    grad.setAttribute("id", gradId);
    grad.setAttribute("x1","0%"); grad.setAttribute("y1","0%");
    grad.setAttribute("x2","100%"); grad.setAttribute("y2","0%");
    const stop1 = document.createElementNS(svgNS, "stop");
    const stop2 = document.createElementNS(svgNS, "stop");
    stop1.setAttribute("offset","0%");
    stop2.setAttribute("offset","100%");
    if(mode === 'pink'){ stop1.setAttribute("stop-color","#ff4d6d"); stop2.setAttribute("stop-color","#ff7aa2"); }
    else if(mode === 'violet'){ stop1.setAttribute("stop-color","#a78bfa"); stop2.setAttribute("stop-color","#7c3aed"); }
    else{ stop1.setAttribute("stop-color","#ff7aa2"); stop2.setAttribute("stop-color","#d03152"); }
    grad.appendChild(stop1); grad.appendChild(stop2);
    defs.appendChild(grad); svg.appendChild(defs);

    const g = document.createElementNS(svgNS, "g");
    svg.appendChild(g);

    const textEl = document.createElementNS(svgNS, "text");
    textEl.setAttribute("x","0");
    textEl.setAttribute("y","0");
    textEl.setAttribute("font-size","140");
    textEl.setAttribute("font-weight","900");
    textEl.setAttribute("fill", "url(#"+gradId+")");
    textEl.setAttribute("text-anchor","middle");
    textEl.setAttribute("dominant-baseline","middle");
    textEl.appendChild(document.createTextNode(text));
    g.appendChild(textEl);

    document.body.appendChild(svg);

    // 两帧后测量，确保布局完成
    requestAnimationFrame(()=>requestAnimationFrame(()=>{
      const bbox   = textEl.getBBox();
      const margin = 40;
      const maxW   = 1000 - margin*2;
      const maxH   = 400  - margin*2;
      const scale  = Math.min(maxW / bbox.width, maxH / bbox.height, 1);
      const centerX = 500, centerY = 200;
      const textCX  = bbox.x + bbox.width/2;
      const textCY  = bbox.y + bbox.height/2;
      const tx = centerX - textCX * scale;
      const ty = centerY - textCY * scale;
      g.setAttribute("transform", "translate("+tx+","+ty+") scale("+scale+")");
    }));

    if(!permanent){
      setTimeout(()=>{
        svg.classList.add('fade-out');
        svg.addEventListener('animationend', ()=> svg.remove(), {once:true});
      }, hold);
    }
  }

  /* ---------- 交互 ---------- */
  function success(){
    popHeart();
    svgPopText(SUCCESS_MSG1, "violet", 1200, false);              // 先紫色，短暂
    setTimeout(()=> svgPopText(SUCCESS_MSG2, "pink", 2000, true), 1200); // 后粉色，常驻
    startHeartRainForever();                                      // 爱心雨一直下
    btn.disabled = true;
    input.disabled = true;
    tip.classList.remove('show');
  }
  function fail(){
    tip.classList.add('show');
    svgPopText(ERROR_POP, "error", 1200, false);
    input.focus();
  }

  btn.addEventListener('click', function(){
    const v = String(input.value || "").trim();
    (v === String(CORRECT)) ? success() : fail();
  });
})();
</script>
</body>
</html>
