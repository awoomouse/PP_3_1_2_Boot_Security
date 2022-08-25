const url = 'http://localhost:8080/api/user'
let loggedInUser = document.querySelector('#User')

fetch(url)
    .then(res => res.json())
    .then(data => {
        loggedInUser.innerHTML = `
                    <td>${data.id}</td> 
                    <td>${data.username}</td> 
                    <td>${data.firstName}</td> 
                    <td>${data.lastName}</td>                    
                    <td>${data.age}</td> 
                    <td>${data.email}</td>                   
                    <td>${data.roles.map(role => role.roleName === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td> 
                                `;
    })