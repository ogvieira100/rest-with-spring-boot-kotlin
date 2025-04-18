package br.com.ogvieira.first.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper

object DozerMapper {

        private  val mapper: Mapper = DozerBeanMapperBuilder.buildDefault()

        fun <S,T> parseObject(source:S, destination: Class<T>?  ) : T{
                return  mapper.map(source, destination)
        }

        fun <S,T> parseListObject(source:List<S>, destination: Class<T>?  ) : List<T>{
                val destinationObjects: ArrayList<T> = ArrayList()
                for (o in source){
                        destinationObjects.add(mapper.map(o, destination))
                }
                return  destinationObjects
        }
}