
window.addEventListener('WebComponentsReady', () => {
  class MyElement extends HTMLElement {
    connectedCallback(){
      this.textContent = 'upgraded!';
      var div = document.createElement('div');

      div.innerHTML="<h1>test something</h1>";
      this.appendChild(div);

    }

  }

  class MyElement2 extends HTMLElement {
    connectedCallback(){
      this.textContent = 'MyElement2!';
      var div = document.createElement('div');

      div.innerHTML="<h1>test MyElement2</h1>";
      this.appendChild(div);

    }

  }
  customElements.define('my-element2', MyElement2);
  customElements.define('my-element', MyElement);
});