<#--<#import "spring.ftl" as spring>-->
<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="row justify-content-around">
        <#list movies.content as movie>
            <div class="card col-3 m-1">
                <img src="/images/${movie.id}" class="card-img-top" alt="${movie.name}">
                <div class="card-body">
                    <h5 class="card-title">${movie.name}</h5>
                    <p class="card-text">${movie.description}</p>
                    <a href="/${movie.id}" class="btn btn-primary"><@spring.message 'main.item.info'/></a>

                </div>
            </div>
        </#list>
    </div>

    <a href="/add" class="btn btn-success"><@spring.message 'main.newItem'/></a>

</@main.layout>